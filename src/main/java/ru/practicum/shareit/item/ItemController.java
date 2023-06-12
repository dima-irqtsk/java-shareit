package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody @Valid ItemDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        item = itemService.add(item, userId);
        return itemMapper.toDto(item);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable("id") long itemId,
                          @RequestBody ItemDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        item = itemService.update(item, userId, itemId);
        return itemMapper.toDto(item);
    }

    @GetMapping
    public List<ItemDto> returnUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemsByUserId(userId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemDto returnById(@PathVariable("id") long itemId) {
        Item item = itemService.getById(itemId);
        return itemMapper.toDto(item);
    }

    @GetMapping("/search")
    public List<ItemDto> returnByQuery(@RequestParam("text") String query) {
        return itemService.getByQuery(query)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }
}
