package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.*;
import com.stroganov.warehouse.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceIml implements ItemService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Logger logger;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    ModelService modelService;

    @Autowired
    private ItemStyleService itemStyleService;

    @Autowired
    private DimensionService dimensionService;

    @Autowired
    private ManufactureService manufactureService;

    @Override
    public void saveAll(Set<Item> itemList) {
        itemRepository.saveAll(itemList);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void saveAllUnique(Set<Item> itemSet) {
        Optional<ItemStyle> itemStyle;
        Optional<Manufacture> manufacture;
        Optional<Model> model;
        Optional<Dimension> dimension = Optional.empty();
        for (Item item : itemSet) {
            itemStyle = itemStyleService.findItemStyleByStyleArticleAndStyleName(
                    item.getItemStyle().getStyleArticle(),
                    item.getItemStyle().getStyleName());
            model = modelService.findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(
                    item.getModel().getArticle(),
                    item.getModel().getDescription(),
                    item.getModel().getDimension().getWidth(),
                    item.getModel().getDimension().getHeight(),
                    item.getModel().getDimension().getDepth());
            manufacture = manufactureService.findByNameAndDescription(
                    item.getProducer().getName(),
                    item.getProducer().getDescription());
            if (model.isEmpty()) {
                dimension = dimensionService.findDimensionByWidthAndHeightAndDepth(
                        item.getModel().getDimension().getWidth(),
                        item.getModel().getDimension().getHeight(),
                        item.getModel().getDimension().getDepth()
                );
            }
            itemStyle.ifPresent(item::setItemStyle);
            model.ifPresent(item::setModel);
            manufacture.ifPresent(item::setProducer);
            dimension.ifPresent(item.getModel()::setDimension);
            itemRepository.save(item);
        }
    }
}
