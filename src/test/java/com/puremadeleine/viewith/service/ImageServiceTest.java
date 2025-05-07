package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.image.ImageEntity;
import com.puremadeleine.viewith.domain.image.SourceType;
import com.puremadeleine.viewith.provider.ImageProvider;
import com.puremadeleine.viewith.provider.S3Uploader;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.puremadeleine.viewith.service.ImageServiceTest.MockImageClass.getImageEntityList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    S3Uploader s3Uploader = mock(S3Uploader.class);
    ImageProvider imageProvider = mock(ImageProvider.class);

    ImageService imageService = new ImageService(s3Uploader, imageProvider);

    @Nested
    @DisplayName("Image Saving Tests")
    class saveImagesTests {

        @DisplayName("Should call saveAll when saving images for the given source Id and source type")
        @Test
        void givenImagesAndSourceId_whenSaveImages_thenSaveImageEntities() {
            // given
            Long sourceId = 1L;
            SourceType sourceType = SourceType.REVIEW;
            String url = "url";
            MultipartFile file1 = mock(MultipartFile.class);
            List<MultipartFile> images = List.of(file1);

            when(s3Uploader.uploadFile(any(MultipartFile.class))).thenReturn(url);

            // when
            imageService.saveImages(images, sourceId, sourceType);

            // then
            verify(imageProvider, times(1)).saveAll(anyList());
        }
    }

    @Nested
    @DisplayName("Image Retrieval Tests")
    class getImageTests {

        @DisplayName("Returns a list of review image URLs for the given source Id")
        @Test
        void givenSourceId_whenGetReviewImageUrlList_thenReturnListOfImageUrls() {
            // given
            int size = 2;
            List<ImageEntity> imageList = getImageEntityList(size);
            when(imageProvider.getImageList(anyLong(), any(SourceType.class))).thenReturn(imageList);

            // when
            List<String> result = imageService.getReviewImageUrlList(1L);

            // then
            assertThat(result).hasSize(2);
        }

        @DisplayName("Should return map grouped by imageId when given sourceIds")
        @Test
        void givenSourceIds_whenGetReviewImageUrlMap_thenReturnMapGroupedByImageId() {
            // given
            int size = 2;
            List<ImageEntity> imageList = getImageEntityList(size);
            when(imageProvider.getImageList(anyList(), any(SourceType.class))).thenReturn(imageList);

            // when
            Map<Long, List<String>> result = imageService.getReviewImageUrlMap(List.of(1L, 2L));

            // then
            assertThat(result.keySet()).hasSize(2);
        }
    }

    static class MockImageClass {

        static List<ImageEntity> getImageEntityList(int size) {
            return Instancio.ofList(ImageEntity.class)
                .size(size)
                .create();
        }
    }
}
