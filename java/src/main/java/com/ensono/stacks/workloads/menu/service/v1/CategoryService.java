package com.ensono.stacks.workloads.menu.service.v1;

import com.ensono.stacks.core.api.dto.response.ResourceCreatedResponse;
import com.ensono.stacks.core.api.dto.response.ResourceUpdatedResponse;
import com.ensono.stacks.workloads.menu.api.v1.dto.request.CreateCategoryRequest;
import com.ensono.stacks.workloads.menu.api.v1.dto.request.UpdateCategoryRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  public ResourceCreatedResponse create(@Valid CreateCategoryRequest body, String correlationId) {
    return new ResourceCreatedResponse(UUID.randomUUID());
  }

  public ResourceUpdatedResponse update(
      UUID menuId, UUID categoryId, @Valid UpdateCategoryRequest body, String correlationId) {
    return new ResourceUpdatedResponse(UUID.randomUUID());
  }
}
