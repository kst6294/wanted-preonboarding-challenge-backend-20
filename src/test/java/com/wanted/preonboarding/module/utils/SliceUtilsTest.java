package com.wanted.preonboarding.module.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SliceUtilsTest {

    @Test
    void toSlice_withContentLessThanPageSize_returnsSliceWithoutNext() {
        // given
        List<String> contents = Arrays.asList("item1", "item2");
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<String> slice = SliceUtils.toSlice(contents, pageable);

        // then
        assertThat(slice.hasNext()).isFalse();
        assertThat(slice.getContent()).isEqualTo(contents);
    }

    @Test
    void toSlice_withContentEqualToPageSize_returnsSliceWithoutNext() {
        // given
        List<String> contents = Arrays.asList("item1", "item2", "item3");
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<String> slice = SliceUtils.toSlice(contents, pageable);

        // then
        assertThat(slice.hasNext()).isFalse();
        assertThat(slice.getContent()).isEqualTo(contents);
    }

    @Test
    void toSlice_withContentGreaterThanPageSize_returnsSliceWithNext() {
        // given
        List<String> contents = Arrays.asList("item1", "item2", "item3", "item4");
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<String> slice = SliceUtils.toSlice(contents, pageable);

        // then
        assertThat(slice.hasNext()).isTrue();
        assertThat(slice.getContent()).isEqualTo(contents.subList(0, 3));
    }

    @Test
    void emptySlice_returnsEmptySlice() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Slice<String> slice = SliceUtils.emptySlice(pageable);

        // then
        assertThat(slice.hasNext()).isFalse();
        assertThat(slice.getContent()).isEmpty();
    }

}