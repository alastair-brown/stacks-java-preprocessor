package com.ensono.stacks.workloads.menu.api.v1;

import static org.springframework.http.HttpStatus.OK;

import com.ensono.stacks.core.api.annotations.CreateAPIResponses;
import com.ensono.stacks.core.api.annotations.DeleteAPIResponses;
import com.ensono.stacks.core.api.annotations.UpdateAPIResponses;
import com.ensono.stacks.core.api.dto.response.ResourceCreatedResponse;
import com.ensono.stacks.core.api.dto.response.ResourceUpdatedResponse;
import com.ensono.stacks.workloads.menu.api.v1.dto.request.CreateItemRequest;
import com.ensono.stacks.workloads.menu.api.v1.dto.request.UpdateItemRequest;
import com.ensono.stacks.workloads.menu.service.v1.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    path = "/v1/menu/{id}/category/{categoryId}/items",
    produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@RestController
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @PostMapping
  @Operation(
      tags = "Item",
      summary = "Add an item to an existing category in a menu",
      description = "Adds a menu item",
      operationId = "AddMenuItem")
  @CreateAPIResponses
  ResponseEntity<ResourceCreatedResponse> createItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Valid @RequestBody CreateItemRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {

    return new ResponseEntity<>(
        itemService.create(menuId, categoryId, body, correlationId), HttpStatus.CREATED);
  }

  @PutMapping("/{itemId}")
  @Operation(
      tags = "Item",
      summary = "Update an item in the menu",
      description = "Update an item in the menu",
      operationId = "UpdateMenuItem")
  @UpdateAPIResponses
  ResponseEntity<ResourceUpdatedResponse> updateItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Parameter(description = "Item id", required = true) @PathVariable("itemId") UUID itemId,
      @Valid @RequestBody UpdateItemRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {

    return new ResponseEntity<>(
        itemService.update(menuId, categoryId, body, correlationId), HttpStatus.OK);
  }

  @DeleteMapping("/{itemId}")
  @Operation(
      tags = "Item",
      summary = "Removes an item from menu",
      description = "Removes an item from menu",
      operationId = "DeleteMenuItem")
  @DeleteAPIResponses
  ResponseEntity<Void> deleteItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Parameter(description = "Item id", required = true) @PathVariable("itemId") UUID itemId,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {

    return new ResponseEntity<>(OK);
  }
}
