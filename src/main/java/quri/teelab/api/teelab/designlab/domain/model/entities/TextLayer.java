package quri.teelab.api.teelab.designlab.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateTextLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerType;

import java.util.UUID;

@Entity
@DiscriminatorValue("TEXT")
@Getter
public class TextLayer extends Layer {
    @Column(nullable = true)
    private String text;

    @Column(nullable = true)
    private String fontColor;

    @Column(nullable = true)
    private String fontFamily;

    @Column(nullable = true)
    private Integer fontSize;

    @Column(nullable = true)
    private Boolean isBold;

    @Column(nullable = true)
    private Boolean isItalic;

    @Column(nullable = true)
    private Boolean isUnderlined;

    protected TextLayer() {
    }

    public TextLayer(CreateTextLayerCommand command) {
        super(new LayerId(UUID.randomUUID()), LayerType.TEXT);
        this.text = command.text();
        this.fontColor = command.fontColor();
        this.fontFamily = command.fontFamily();
        this.fontSize = command.fontSize();
        this.isBold = command.isBold();
        this.isItalic = command.isItalic();
        this.isUnderlined = command.isUnderlined();
    }
    public void updateDetails(String text, String fontColor, String fontFamily, Integer fontSize, Boolean isBold, Boolean isItalic, Boolean isUnderlined) {
        this.text = text;
        this.fontColor = fontColor;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderlined = isUnderlined;
    }
}
