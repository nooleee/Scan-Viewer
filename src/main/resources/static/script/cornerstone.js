import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

const {
    ZoomTool, ToolGroupManager,
    Enums: csToolsEnums,
    LengthTool, AngleTool,
    MagnifyTool,StackScrollMouseWheelTool
} = cornerstoneTools;
const {MouseBindings} = csToolsEnums;

// 뷰 포트 생성
const toolGroupId = 'myToolGroup';
const renderingEngineId = 'myRenderingEngine';
const viewportId = 'CT_AXIAL_STACK';
const content = document.getElementById('dicomViewport');
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
cornerstoneTools.addTool(StackScrollMouseWheelTool);

const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
toolGroup.addTool(ZoomTool.toolName);
toolGroup.addTool(LengthTool.toolName);
toolGroup.addTool(AngleTool.toolName);
toolGroup.addTool(MagnifyTool.toolName);
toolGroup.addTool(StackScrollMouseWheelTool.toolName);

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
    await viewport.setStack(imageIds);

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
    toolGroup.setToolActive(StackScrollMouseWheelTool.toolName);

};

const loadSeries = async (studykey, serieskey) => {
    const response = await fetch(`/images/${studykey}/${serieskey}/dicom-urls`);
    const dicomUrls = await response.json();

    const imageIds = dicomUrls.map(url => `dicomweb:/images/dicom-file?path=${encodeURIComponent(url)}`);
    console.log("imageIds size : " + imageIds.length)
    console.log("imageIds : " + imageIds)
    await render(imageIds);
};

// URL 경로에서 studykey와 serieskey를 추출하는 함수
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

    // // 시리즈 로드 (예시로 studykey와 serieskey를 사용)
    // const studykey = 7;  // 실제 값을 넣어주세요
    // const serieskey = 1;  // 실제 값을 넣어주세요
    // loadSeries(studykey, serieskey);

    // URL 경로에서 studykey와 serieskey 추출
    const keys = extractKeysFromPath();
    if (keys) {
        const { studykey, serieskey } = keys;
        await loadSeries(studykey, serieskey);
    } else {
        console.error('studykey와 serieskey를 추출할 수 없습니다.');
    }
};

init();
