package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

public record CreateOrderIntentCommand(Money amount){
}
