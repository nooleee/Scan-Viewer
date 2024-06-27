import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

const {
    ZoomTool, ToolGroupManager,
    Enums: csToolsEnums,
    LengthTool, AngleTool,
    MagnifyTool
} = cornerstoneTools;
const {MouseBindings} = csToolsEnums;

// 뷰 포트 생성
const toolGroupId = 'myToolGroup';
const renderingEngineId = 'myRenderingEngine';
const viewportId = 'CT_AXIAL_STACK';
const content = document.getElementById('content');
const element = document.createElement('div');
element.style.width = '500px';
element.style.height = '500px';
element.oncontextmenu = (e) => e.preventDefault();
content.appendChild(element);

cornerstoneTools.init();
cornerstoneTools.addTool(ZoomTool);
cornerstoneTools.addTool(LengthTool);
cornerstoneTools.addTool(AngleTool);
cornerstoneTools.addTool(MagnifyTool);

const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
toolGroup.addTool(ZoomTool.toolName);
toolGroup.addTool(LengthTool.toolName);
toolGroup.addTool(AngleTool.toolName);
toolGroup.addTool(MagnifyTool.toolName);

const render = async (imageIds) => {
    const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);
    const viewportInput = {
        viewportId,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    toolGroup.addViewport(viewportId, renderingEngineId);
    renderingEngine.enableElement(viewportInput);

    await renderingEngine.renderViewports([viewportId]);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);
    viewport.setStack(imageIds);

    cornerstoneTools.utilities.stackPrefetch.enable(viewport.element);

    await viewport.render();

    // 도구 활성화 설정 (뷰포트가 렌더링된 후)
    toolGroup.setToolActive(ZoomTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Primary }],
    }); // 좌클릭으로 줌
    toolGroup.setToolActive(MagnifyTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Secondary }],
    }); // 우클릭으로 확대
    toolGroup.setToolActive(AngleTool.toolName, {
        bindings: [{ mouseButton: MouseBindings.Auxiliary }],
    });
};

const loadSeries = async (studykey, serieskey) => {
    const response = await fetch(`/images/study/${studykey}/series/${serieskey}/dicom-urls`);
    const dicomUrls = await response.json();

    const imageIds = dicomUrls.map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);
    await render(imageIds);
};

const init = async () => {
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

    // 시리즈 로드 (예시로 studykey와 serieskey를 사용)
    const studykey = 5;  // 실제 값을 넣어주세요
    const serieskey = 1;  // 실제 값을 넣어주세요
    loadSeries(studykey, serieskey);
};

init();
