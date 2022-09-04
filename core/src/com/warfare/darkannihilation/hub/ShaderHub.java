package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.BLUR_SHADER_FRAG;
import static com.warfare.darkannihilation.constants.Assets.BLUR_SHADER_VERT;

import com.badlogic.gdx.assets.loaders.ShaderProgramLoader.ShaderProgramParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.warfare.darkannihilation.systemd.service.Processor;

public class ShaderHub extends BaseHub {
    public ShaderProgram blurShader;

    public ShaderHub(AssetManagerSuper assetManager) {
        super(assetManager);
    }

    private ShaderProgramParameter getShaderProgramParameter(String vertexFile, String fragmentFile) {
        ShaderProgramParameter shaderProgramParameter = new ShaderProgramParameter();
        shaderProgramParameter.vertexFile = vertexFile;
        shaderProgramParameter.fragmentFile = fragmentFile;

        return shaderProgramParameter;
    }

    private void checkOnErrors(ShaderProgram shaderProgram) {
        if (!shaderProgram.isCompiled() || shaderProgram.getLog().length() != 0) {
            Processor.postTask(() -> {
                throw new RuntimeException(shaderProgram.getLog());
            });
        }
    }

    public void loadBlurShader() {
        assetManager.load("blur", ShaderProgram.class, getShaderProgramParameter(BLUR_SHADER_VERT, BLUR_SHADER_FRAG));
    }

    public void getBlurShader() {
        blurShader = assetManager.get("blur", ShaderProgram.class);
        checkOnErrors(blurShader);
    }

    public void disposeBlurShader() {
        assetManager.unload("blur");
    }
}
