package org.rangiffler.condition;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.CollectionSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.rangiffler.model.PhotoJson;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhotoCondition {

    public static CollectionCondition photo(PhotoJson expectedImage) {
        return new CollectionCondition() {

            @Override
            public boolean test(List<WebElement> elements) {
                if (elements.size() != 1) {
                    return false;
                }
                for (int i = 0; i < elements.size(); i++) {
                    WebElement item = elements.get(i);
                    if (!item.findElement(By.cssSelector("img")).getAttribute("src").equals(expectedImage.getPhoto())) {
                        return false;
                    }
                    if (!item.findElement(By.cssSelector("img")).getAttribute("alt").equals(expectedImage.getDescription())) {
                        return false;
                    }
                    if (!item.findElement(By.cssSelector("p")).getText().equals(expectedImage.getCountryJson().getName())) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void fail(CollectionSource collection, @Nullable List<WebElement> elements, @Nullable Exception lastError, long timeoutMs) {
                if (elements == null || elements.isEmpty()) {
                    throw new ElementNotFound(collection, List.of("Can`t find element"), lastError);
                }
                else if (elements.size() != 1) {
                    throw new PhotosSizeMismatch(collection, Arrays.asList(expectedImage), bindElementsToPhotos(elements), explanation, timeoutMs);
                } else {
                    throw new PhotosMismatch(collection, Arrays.asList(expectedImage), bindElementsToPhotos(elements), explanation, timeoutMs);
                }
            }

            @Override
            public boolean missingElementSatisfiesCondition() {
                return false;
            }

            private List<PhotoJson> bindElementsToPhotos(List<WebElement> elements) {
                return elements.stream()
                        .map(e -> {
                            WebElement cell = e.findElement(By.cssSelector("p"));
                            PhotoJson actual = new PhotoJson();
                            actual.setPhoto(cell.getText());
                            return actual;
                        }).collect(Collectors.toList());
            }
        };
    }
}
