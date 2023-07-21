package org.rangiffler.jupiter.extension;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.model.PhotoJson;

import java.util.Objects;

public class GeneratePhotoExtension implements BeforeEachCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(GeneratePhotoExtension.class);
    private static final GeneratePhotoService generatePhotoService = new GeneratePhotoService();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GeneratePhoto annotation = context.getRequiredTestMethod()
                .getAnnotation(GeneratePhoto.class);

        if (annotation != null) {
            PhotoJson photoJson = generatePhotoService.generatePhoto();
            context.getStore(NAMESPACE).put(getTestId(context), photoJson);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(PhotoJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(getTestId(extensionContext), PhotoJson.class);
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }

}
