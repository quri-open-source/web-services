package quri.teelab.api.teelab.orderfulfillment.application.internal.queryservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllFulfillmentItemsByFulfillmentIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemQueryService;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.FulfillmentItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FulfillmentItemQueryServiceImpl implements FulfillmentItemQueryService {

    private final FulfillmentItemRepository fulfillmentItemRepository;

    @Override
    public List<FulfillmentItem> handle(GetAllFulfillmentItemsByFulfillmentIdQuery query) {
        return fulfillmentItemRepository.findByFulfillmentId(query.fulfillmentId());
    }
}
