import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

const {
    ZoomTool, PanTool, LengthTool, AngleTool, MagnifyTool, ToolGroupManager, StackScrollMouseWheelTool,
    WindowLevelTool,
    Enums: csToolsEnums
} = cornerstoneTools;
const { MouseBindings } = csToolsEnums;

const toolGroupId = 'myToolGroup';
const renderingEngineId = 'myRenderingEngine';
const viewportId = 'CT_AXIAL_STACK';

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
    cornerstoneTools.addTool(ZoomTool);
    cornerstoneTools.addTool(PanTool);
    cornerstoneTools.addTool(LengthTool);
    cornerstoneTools.addTool(AngleTool);
    cornerstoneTools.addTool(MagnifyTool);
    cornerstoneTools.addTool(WindowLevelTool);
    cornerstoneTools.addTool(StackScrollMouseWheelTool);

    const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
    toolGroup.addTool(ZoomTool.toolName);
    toolGroup.addTool(PanTool.toolName);
    toolGroup.addTool(LengthTool.toolName);
    toolGroup.addTool(AngleTool.toolName);
    toolGroup.addTool(MagnifyTool.toolName);
    toolGroup.addTool(WindowLevelTool.toolName);
    toolGroup.addTool(StackScrollMouseWheelTool.toolName);
};

const render = async (imageIds, element) => {
    const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);
    renderingEngine.disableElement(viewportId);
    const viewportInput = {
        viewportId,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    toolGroup.addViewport(viewportId, renderingEngineId);
    renderingEngine.enableElement(viewportInput);

    await renderingEngine.renderViewports([viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);
    await viewport.setStack(imageIds);

    cornerstoneTools.utilities.stackPrefetch.enable(viewport.element);

    await viewport.render();

    toolGroup.setToolActive(ZoomTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Primary }],
    });
    toolGroup.setToolActive(MagnifyTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Secondary }],
    });
    toolGroup.setToolActive(AngleTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Auxiliary }],
    });
    toolGroup.setToolActive(PanTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Primary_And_Secondary }],
    });
    toolGroup.setToolActive(WindowLevelTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Primary_And_Auxiliary }],
    });
    toolGroup.setToolActive(StackScrollMouseWheelTool.toolName);
};

const renderThumbnail = async (imageIds, elementId) => {
    const renderingEngine = new cornerstone.RenderingEngine(`${renderingEngineId}-${elementId}`);
    const viewportInput = {
        viewportId: `thumbnail-${elementId}`,
        element: document.getElementById(elementId),
        type: cornerstone.Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);
    await renderingEngine.renderViewports([viewportInput.viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);
    await viewport.setStack(imageIds);

    await viewport.render();
};

// const extractMetaData = async (url) => {
//     try {
//         const image = await cornerstone.loadAndCacheImage(url);
//         console.log(`Extracted metadata for ${url}:`, image.data);
//     } catch (error) {
//         console.error(`Failed to extract metadata for ${url}:`, error);
//     }
// };

const loadSeries = async (studykey, index, element) => {
    try {
        const response = await fetch(`/images/${studykey}/${index}/dicom-urls`);
        if (!response.ok) {
            throw new Error(`Invalid index: ${index}`);
        }
        const dicomUrls = await response.json();
        const imageIds = dicomUrls.map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);


        // // Extract metadata for debugging
        // for (const url of imageIds) {
        //     await extractMetaData(url);
        // }

        await render(imageIds, element);
    } catch (error) {
        console.error("Failed to load series:", error);
        alert('잘못된 인덱스입니다. 유효한 시리즈를 선택해주세요.');
    }
};

const loadThumbnails = async (seriesList) => {
    for (const series of seriesList) {
        const response = await fetch(`/images/${series.studyKey}/${series.index}/dicom-urls`);
        if (response.ok) {
            const dicomUrls = await response.json();
            const imageIds = dicomUrls.map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);
            await renderThumbnail(imageIds, `thumbnail-${series.index}`);
        }
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

    const keys = extractKeysFromPath();
    if (keys) {
        const { studykey, serieskey } = keys;
        const contentElement = document.getElementById('dicomViewport');
        await loadSeries(studykey, serieskey, contentElement);
    } else {
        console.error('studykey와 serieskey를 추출할 수 없습니다.');
    }

    const seriesList = Array.from(document.querySelectorAll('.thumbnail-viewport')).map(thumbnail => ({
        studyKey: keys.studykey,
        index: thumbnail.getAttribute('data-series-index')
    }));

    await loadThumbnails(seriesList);

    document.querySelectorAll('.thumbnail-viewport').forEach(thumbnail => {
        thumbnail.addEventListener('click', async () => {
            const index = thumbnail.getAttribute('data-series-index');

            console.log("index : " + index);
            const keys = extractKeysFromPath();
            if (keys) {
                const { studykey } = keys;
                const contentElement = document.getElementById('dicomViewport');
                await loadSeries(studykey, index, contentElement);
            }
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

document.getElementById('zoomTool').addEventListener('click', () => toolAction(ZoomTool.toolName));
document.getElementById('panTool').addEventListener('click', () => toolAction(PanTool.toolName));
document.getElementById('lengthTool').addEventListener('click', () => toolAction(LengthTool.toolName));
document.getElementById('angleTool').addEventListener('click', () => toolAction(AngleTool.toolName));
document.getElementById('magnifyTool').addEventListener('click', () => toolAction(MagnifyTool.toolName));
document.getElementById('stackScrollTool').addEventListener('click', () => toolAction(StackScrollMouseWheelTool.toolName));

document.addEventListener('DOMContentLoaded', init);
