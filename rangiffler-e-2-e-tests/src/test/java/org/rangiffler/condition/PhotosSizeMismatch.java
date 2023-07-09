package org.rangiffler.condition;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;
import org.rangiffler.model.PhotoJson;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static java.lang.System.lineSeparator;

@ParametersAreNonnullByDefault
public class PhotosSizeMismatch extends UIAssertionError {
    public PhotosSizeMismatch(CollectionSource collection,
                              List<PhotoJson> expectedPhotos, List<PhotoJson> actualPhotos,
                              @Nullable String explanation, long timeoutMs) {
        super(
                collection.driver(),
                "Photos list size mismatch" +
                        lineSeparator() + "Actual: " + actualPhotos + ", List size: " + actualPhotos.size() +
                        lineSeparator() + "Expected: " + expectedPhotos + ", List size: " + expectedPhotos.size() +
                        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
                        lineSeparator() + "Collection: " + collection.description(),
                expectedPhotos, actualPhotos,
                timeoutMs
        );
    }
}
