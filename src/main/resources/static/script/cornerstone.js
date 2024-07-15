import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

const {
    ZoomTool, PanTool, LengthTool, AngleTool,
    MagnifyTool, ToolGroupManager,
    StackScrollMouseWheelTool,
    StackScrollTool,
    WindowLevelTool,
    Enums: csToolsEnums
} = cornerstoneTools;
const { MouseBindings } = csToolsEnums;

const toolGroupId = 'myToolGroup';
const renderingEngineId = 'myRenderingEngine';
let viewports = ['viewport1'];
let seriesList = [];
let allImages = {};
let selectedToolName = PanTool.toolName;

// 데이터를 초기화합니다.
const initializeData = async (studykey) => {
    try {
        const response = await fetch(`/images/studies/${studykey}/series`);
        if (!response.ok) {
            throw new Error('Failed to fetch series data');
        }
        allImages = await response.json();
        console.log('[DEBUG] allImages: ', allImages);
    } catch (error) {
        console.error('Error initializing data:', error);
    }
};

const initializeCornerstone = async () => {
    await cornerstone.init();

    cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
    cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;

    const config = {
        maxWebWorkers: navigator.hardwareConcurrency || 1,
        startWebWorkersOnDemand: true,
        taskConfiguration: {
            decodeTask: {
                initializeCodecsOnStartup: false,
            },
            sleepTask: {
                sleepTime: 3000,
            },
        },
    };
    cornerstoneDICOMImageLoader.webWorkerManager.initialize(config);
    cornerstoneTools.init();

    const tools = [
        { tool: ZoomTool, options: {} },
        { tool: PanTool, options: {} },
        { tool: LengthTool, options: {} },
        { tool: AngleTool, options: {} },
        { tool: MagnifyTool, options: {} },
        { tool: WindowLevelTool, options: {} },
        { tool: StackScrollMouseWheelTool, options: {} },
        { tool: StackScrollTool, options: {} }
    ];

    tools.forEach(({ tool, options }) => {
        if (!cornerstoneTools.state.tools[tool.toolName]) {
            cornerstoneTools.addTool(tool, options);
        }
    });

    let toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    if (!toolGroup) {
        toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
        tools.forEach(({ tool }) => toolGroup.addTool(tool.toolName));
    }
};

const render = async (imageIds, element, viewportId) => {
    const renderingEngine = cornerstone.getRenderingEngine(renderingEngineId);

    const viewportInput = {
        viewportId,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);
    const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    toolGroup.addViewport(viewportId, renderingEngineId);

    await renderingEngine.renderViewports([viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);
    await viewport.setStack(imageIds);

    await viewport.render();

    toolGroup.setToolActive(selectedToolName, {
        bindings: [{ mouseButton: MouseBindings.Primary }],
    });
    toolGroup.setToolActive(StackScrollMouseWheelTool.toolName);
};

const renderThumbnail = async (imageIds, elementId) => {
    const element = document.getElementById(elementId);
    if (!element) {
        console.error(`Element with ID ${elementId} not found`);
        return;
    }

    const renderingEngine = new cornerstone.RenderingEngine(`${renderingEngineId}-${elementId}`);
    const viewportInput = {
        viewportId: `thumbnail-${elementId}`,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);
    await renderingEngine.renderViewports([viewportInput.viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);
    await viewport.setStack(imageIds);

    await viewport.render();
};

const loadSeries = async (serieskey, element, viewportId) => {
    try {
        if (!allImages[serieskey]) {
            throw new Error(`No images found for serieskey: ${serieskey}`);
        }
        const imageIds = allImages[serieskey].map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);
        await render(imageIds, element, viewportId);
    } catch (error) {
        console.error("Failed to load series:", error);
        alert('잘못된 시리즈키입니다. 유효한 시리즈를 선택해주세요.');
    }
};

const loadThumbnails = async (seriesList) => {
    for (const series of seriesList) {
        if (!allImages[series.seriesKey]) {
            console.warn(`No images found for seriesKey: ${series.seriesKey}`);
            continue;
        }
        const imageIds = allImages[series.seriesKey].map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);
        await renderThumbnail(imageIds, `thumbnail-${series.seriesKey}`);
    }
};

const extractKeysFromPath = () => {
    const path = window.location.pathname; // 예: "/images/5/1"
    const pathParts = path.split('/');
    if (pathParts.length >= 4) {
        const studykey = pathParts[2];
        const serieskey = pathParts[3];
        return { studykey, serieskey };
    } else {
        console.error('올바른 경로 형식이 아닙니다. 예: /images/{studykey}/{serieskey}');
        return null;
    }
};

const init = async () => {
    await initializeCornerstone();
    const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);
    const keys = extractKeysFromPath();
    if (keys) {
        const { studykey, serieskey } = keys;
        await initializeData(studykey);
        const contentElement = document.getElementById('dicomViewport1');
        await loadSeries(serieskey, contentElement, 'viewport1');
    } else {
        console.error('studykey와 serieskey를 추출할 수 없습니다.');
    }

    seriesList = Array.from(document.querySelectorAll('.thumbnail-viewport')).map(thumbnail => ({
        studyKey: keys.studykey,
        seriesKey: thumbnail.getAttribute('data-series-key')
    }));

    await loadThumbnails(seriesList);

    document.querySelectorAll('.thumbnail-viewport').forEach(thumbnail => {
        thumbnail.addEventListener('click', async () => {
            const seriesKey = thumbnail.getAttribute('data-series-key');
            const contentElement = document.getElementById('dicomViewport1');
            await loadSeries(seriesKey, contentElement, 'viewport1');
        });
    });
};

document.getElementById('backButton').addEventListener('click', () => {
    window.location.href = '/worklist';
});

document.getElementById('toggleThumbnails').addEventListener('click', () => {
    const thumbnails = document.getElementById('thumbnails');
    thumbnails.classList.toggle('hidden');
});

const toolAction = (tool) => {
    const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    toolGroup.setToolActive(tool, { bindings: [{ mouseButton: MouseBindings.Primary }] });
};

document.getElementById("toolbar").addEventListener('click', (e)=>{
    if(e.target.className === "tools"){
        const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
        toolGroup.setToolPassive(selectedToolName);
        selectedToolName = e.target.id;
        toolGroup.setToolActive(selectedToolName, { bindings: [{ mouseButton: MouseBindings.Primary }] });
    }
});

document.addEventListener('DOMContentLoaded', init);

document.getElementById('report').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'block';
});

document.querySelector('.close').addEventListener('click', function() {
    document.getElementById('reportModal').style.display = 'none';
});

// window.onclick = function(event) {
//     if (event.target == document.getElementById('reportModal')) {
//         document.getElementById('reportModal').style.display = 'none';
//     }
// };

// 그리드 선택 모달창
const modal = document.getElementById("gridModal");
const gridOptions = document.querySelectorAll(".grid-option");

document.getElementById('layoutButton').addEventListener('click', () => {
    modal.style.display = "block";
});

gridOptions.forEach(option => {
    option.addEventListener('click', () => {
        const [rows, cols] = option.getAttribute('data-grid').split('x').map(Number);
        setGridLayout(rows, cols);
    });
});

window.onclick = function(event) {
    if (event.target == gridModal) {
        gridModal.style.display = "none";
    }
};

// 그리드 레이아웃 설정 함수
const setGridLayout = (rows, cols) => {
    const mainContent = document.getElementById('mainContent');
    mainContent.innerHTML = '';

    viewports = [];
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
            const viewportId = `dicomViewport${i * cols + j + 1}`;
            viewports.push(viewportId);
            const viewportDiv = document.createElement('div');
            viewportDiv.id = viewportId;
            viewportDiv.className = 'viewport';
            mainContent.appendChild(viewportDiv);
        }
    }

    const keys = extractKeysFromPath();
    if (keys) {
        const { studykey, serieskey } = keys;
        const seriesKeys = seriesList.map(series => series.seriesKey);
        viewports.forEach(async (viewportId, i) => {
            const contentElement = document.getElementById(viewportId);
            const currentSeriesKey = seriesKeys[(seriesKeys.indexOf(serieskey) + i) % seriesKeys.length];
            await loadSeries(currentSeriesKey, contentElement, viewportId);
        });
    }

    modal.style.display = "none";
};

document.getElementById('grid-container').addEventListener('click', (e) => {
    if(e.target.className === "grid-option" ){
        const row = parseInt(e.target.dataset.row);
        const col = parseInt(e.target.dataset.col);
        setGridLayout(row,col);
    }
});

$(document).ready(function() {
    $('#report').click(function() {
        const studyKey = window.location.pathname.split('/')[2];
        $.ajax({
            url: `/report/check/${studyKey}`,
            method: 'GET',
            success: function(response) {
                const modalContent = $('#reportContainer');
                modalContent.empty();
                modalContent.load(`/report/view/${studyKey}`, function() {
                    fetchUserInfo(); // 유저 코드 가져오기 함수 호출
                });
                $('#reportModal').show();
            },
            error: function() {
                alert('리포트를 불러오는 도중 에러가 발생했습니다.');
            }
        });
    });

    $('.close').click(function() {
        $('#reportModal').hide();
    });

    $(window).click(function(event) {
        if (event.target === $('#reportModal')[0]) {
            $('#reportModal').hide();
        }
    });
});