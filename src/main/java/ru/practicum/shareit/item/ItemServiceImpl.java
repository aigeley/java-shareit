package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.element.ElementServiceAbs;
import ru.practicum.shareit.item.exception.ItemOwnerIsDifferentException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;

import static ru.practicum.shareit.item.ItemMapper.toItem;
import static ru.practicum.shareit.item.ItemMapper.toItemDto;
import static ru.practicum.shareit.item.ItemMapper.toItemsDtoList;

@Slf4j
@Service
public class ItemServiceImpl extends ElementServiceAbs<Item> implements ItemService {
    public static final String ELEMENT_NAME = "вещь";
    protected final ItemRepository itemRepository;
    protected final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        super(ELEMENT_NAME, itemRepository);
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    protected Item getItemBelongsToUser(long itemId, long userId) {
        Item item = getElement(itemId);

        if (item.getOwner().getId() != userId) {
            throw new ItemOwnerIsDifferentException(elementName, itemId, userId);
        }

        return item;
    }

    @Override
    public ItemDto get(long itemId) {
        return toItemDto(getElement(itemId));
    }

    @Override
    public Collection<ItemDto> getAll(long userId) {
        return toItemsDtoList(itemRepository.getAll(userId));
    }

    @Override
    public Collection<ItemDto> search(String text) {
        return toItemsDtoList(itemRepository.search(text));
    }

    @Override
    public ItemDto add(long userId, ItemDto itemDto) {
        User owner = userService.getElement(userId);
        Item item = toItem(itemDto, itemRepository.getNextId(), owner, null);
        log.info("add: " + item);
        return toItemDto(itemRepository.add(item));
    }

    @Override
    public ItemDto update(long itemId, long userId, ItemDto itemDto) {
        Item oldItem = getItemBelongsToUser(itemId, userId);
        User owner = userService.getElement(userId);
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        Boolean available = itemDto.getAvailable();

        Item item = new Item(
                itemId,
                name != null ? name : oldItem.getName(),
                description != null ? description : oldItem.getDescription(),
                available != null ? available : oldItem.getAvailable(),
                owner,
                null
        );

        log.info("update: " + item);
        return toItemDto(itemRepository.update(item));
    }
}