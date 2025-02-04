package com.ensono.stacks.workloads.menu.api.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author ArathyKrishna */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
  @JsonProperty("name")
  @NotBlank
  private String name = null;

  @JsonProperty("description")
  @NotBlank
  private String description = null;
}
