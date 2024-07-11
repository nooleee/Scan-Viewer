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

    console.log("[65]viewportId : " + viewportId);
    const viewportInput = {
        viewportId,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    console.log("[72]element : " + element);
    // console.dir(element);

    renderingEngine.enableElement(viewportInput);
    const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    toolGroup.addViewport(viewportId, renderingEngineId);

    await renderingEngine.renderViewports([viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);


    await viewport.setStack(imageIds);

    // cornerstoneTools.utilities.stackPrefetch.enable(viewport.element);

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
        console.log("[172]contentElement : " + contentElement);
        await loadSeries(serieskey, contentElement, 'viewport1');
    } else {
        console.error('studykey와 serieskey를 추출할 수 없습니다.');
    }

    seriesList = Array.from(document.querySelectorAll('.thumbnail-viewport')).map(thumbnail => ({
        studyKey: keys.studykey,
        seriesKey: thumbnail.getAttribute('data-series-key')
    }));

    console.log("[178]seriesList : " + JSON.stringify(seriesList));

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

// document.getElementById('layoutButton').addEventListener('click', () => {
//     const layoutMenu = document.getElementById('layoutMenu');
//     layoutMenu.classList.toggle('hidden');
// });

// const setLayout = (layout) => {
//     const mainContent = document.getElementById('mainContent');
//     mainContent.innerHTML = '';
//
//     switch (layout) {
//         case 'one':
//             viewports = ['viewport1'];
//             mainContent.innerHTML = '<div id="dicomViewport1" class="viewport"></div>';
//             break;
//         case 'two':
//             viewports = ['viewport1', 'viewport2'];
//             mainContent.innerHTML = `
//                 <div id="dicomViewport1" class="viewport"></div>
//                 <div id="dicomViewport2" class="viewport"></div>
//             `;
//             break;
//         case 'four':
//             viewports = ['viewport1', 'viewport2', 'viewport3', 'viewport4'];
//             mainContent.innerHTML = `
//                 <div id="dicomViewport1" class="viewport"></div>
//                 <div id="dicomViewport2" class="viewport"></div>
//                 <div id="dicomViewport3" class="viewport"></div>
//                 <div id="dicomViewport4" class="viewport"></div>
//             `;
//             break;
//     }
//
//     const keys = extractKeysFromPath();
//     console.log("[244]seriesList : " + JSON.stringify(seriesList));
//     if (keys) {
//         const { studykey, serieskey } = keys;
//         const seriesKeys = seriesList.map(series => series.seriesKey);
//         viewports.forEach(async (viewportId, i) => {
//             console.log("viewportId : ", viewportId);
//             const slicedViewportId = `dicomV${viewportId.slice(1, 9)}`;
//             console.log("sliced viewportId : ", slicedViewportId);
//
//             const contentElement = document.getElementById(slicedViewportId);
//             console.log(contentElement);
//             const currentSeriesKey = seriesKeys[(seriesKeys.indexOf(serieskey) + i) % seriesKeys.length];
//             console.log("[267]element : " + currentSeriesKey)
//             await loadSeries(currentSeriesKey, contentElement, slicedViewportId);
//         });
//     }
// };

// document.getElementById('layoutOne').addEventListener('click', () => setLayout('one'));
// document.getElementById('layoutTwo').addEventListener('click', () => setLayout('two'));
// document.getElementById('layoutFour').addEventListener('click', () => setLayout('four'));

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
})

document.addEventListener('DOMContentLoaded', init);

document.getElementById('report').addEventListener('click', function() {
    const currentUrl = window.location.href;
    const parts = currentUrl.split('/');
    const studyKey = parts[parts.length - 2];

    window.location.href = '/report/' + studyKey;
});

// 모달 및 그리드 컨테이너 요소
const modal = document.getElementById("gridSelectionModal");
const gridContainer = document.querySelector(".grid-container");

// 모달 닫기 버튼
const closeButton = document.querySelector(".close");

// 모달 열기 버튼
document.getElementById('layoutButton').addEventListener('click', () => {
    modal.style.display = "block";
});

// 모달 닫기
closeButton.addEventListener('click', () => {
    modal.style.display = "none";
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    if (event.target == modal) {
        modal.style.display = "none";
    }
});

// 그리드 버튼 생성 (3x3 예시)
for (let rows = 1; rows <= 5; rows++) {
    for (let cols = 1; cols <= 5; cols++) {
        const gridButton = document.createElement('button');
        gridButton.classList.add('grid-button');
        gridButton.innerText = `${rows}x${cols}`;
        gridButton.addEventListener('click', () => setGridLayout(rows, cols));
        gridContainer.appendChild(gridButton);
    }
}

function setGridLayout(rows, cols) {
    const mainContent = document.getElementById('mainContent');
    mainContent.innerHTML = '';

    const totalViewports = rows * cols;
    for (let i = 0; i < totalViewports; i++) {
        const viewport = document.createElement('div');
        viewport.classList.add('viewport');
        viewport.id = `dicomViewport${i + 1}`;
        mainContent.appendChild(viewport);
    }

    // Flexbox를 이용하여 그리드 배치
    mainContent.style.display = 'grid';
    mainContent.style.gridTemplateRows = `repeat(${rows}, 1fr)`;
    mainContent.style.gridTemplateCols = `repeat(${cols}, 1fr)`;

    modal.style.display = "none";  // 모달 닫기
}