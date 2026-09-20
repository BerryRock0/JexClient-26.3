package me.dustin.jex.feature.mod.impl.render;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import me.dustin.events.core.EventListener;
import me.dustin.events.core.annotate.EventPointer;
import me.dustin.jex.event.filters.TickFilter;
import me.dustin.jex.event.misc.EventSetScreen;
import me.dustin.jex.event.misc.EventTick;
import me.dustin.jex.event.render.EventRenderWidget;
import me.dustin.jex.feature.mod.core.Category;
import me.dustin.jex.feature.mod.core.Feature;
import me.dustin.jex.feature.property.Property;
import me.dustin.jex.helper.math.ColorHelper;
import me.dustin.jex.helper.misc.Wrapper;
import me.dustin.jex.helper.render.Render2DHelper;
import me.dustin.jex.helper.render.font.FontHelper;
import me.dustin.jex.load.impl.ISliderWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.SliderButton;

public class CustomWidgets extends Feature {

    public final Property<Float> shrinkSpeedProperty = new Property.PropertyBuilder<Float>(this.getClass())
                    .name("Shrink Speed")
                    .value(4.0f)
                    .min(1.0f)
                    .max(10.0f)
                    .inc(0.1f)
                    .build();

    public final Property<Float> growSpeedProperty = new Property.PropertyBuilder<Float>(this.getClass())
                    .name("Grow Speed")
                    .value(1.2f)
                    .min(1.0f)
                    .max(10.0f)
                    .inc(0.1f)
                    .build();

    private final Map<AbstractWidget, Float> hoverProgress = new HashMap<>();
    private final Map<AbstractWidget, Float> offsets = new HashMap<>();

    public CustomWidgets() {
        super("CustomWidgets",Category.VISUAL, "Change the visuals on widgets like buttons from Minecraft", true, false, 0);
    }

    @EventPointer
    private final EventListener<EventRenderWidget> eventRenderWidgetEventListener =
            new EventListener<>(event -> {
                int hoverColor = ColorHelper.INSTANCE.setAlpha(
                        ColorHelper.INSTANCE.getClientColor(),
                        150
                );

                int nonHoverColor = 0x80000000;
                int borderColor = 0xFF999999;
                int activeTextColor = 0xFFBBBBBB;
                int inactiveTextColor = 0xFF333333;

                AbstractWidget widget = event.getAbstractWidget();

                /*
                 * EventRenderWidget should return the GuiGraphics used by
                 * Minecraft's 26.3 widget rendering methods.
                 */
                GuiGraphics graphics = event.getGuiGraphics();

                float hover = hoverProgress.getOrDefault(widget, 0.0f);
                float offset = offsets.getOrDefault(widget, 0.0f);

                float targetOffset = hover > 0.0f
                        ? 5.0f * (hover / 10.0f)
                        : 0.0f;

                float tickDelta = Wrapper.INSTANCE
                        .getMinecraft()
                        .getDeltaTracker()
                        .getGameTimeDeltaPartialTick(false);

                offset += (targetOffset - offset) * tickDelta;
                offsets.put(widget, offset);

                int x = widget.getX();
                int y = widget.getY();
                int width = widget.getWidth();
                int height = widget.getHeight();

                if (widget instanceof Button || widget instanceof CycleButton<?>) {
                    Render2DHelper.INSTANCE.fillAndBorder(
                            graphics,
                            x + offset,
                            y + offset / 3.0f,
                            x + width - offset,
                            y + height - offset / 3.0f,
                            borderColor,
                            widget.active && widget.isHovered()
                                    ? hoverColor
                                    : nonHoverColor,
                            1
                    );
                } else if (widget instanceof AbstractSliderButton slider) {
                    double value = ((ISliderWidget) slider).getValue();

                    float sliderX = x + 4.0f;
                    float sliderWidth = width - 8.0f;
                    float sliderPosition = (float) value * sliderWidth;

                    Render2DHelper.INSTANCE.fillAndBorder(
                            graphics,
                            sliderX - 1.0f,
                            y,
                            sliderX + sliderWidth + 1.0f,
                            y + height,
                            borderColor,
                            widget.active
                                    ? nonHoverColor
                                    : 0x99545454,
                            1
                    );

                    Render2DHelper.INSTANCE.fill(
                            graphics,
                            sliderX,
                            y + 1.0f,
                            sliderX + sliderPosition,
                            y + height - 1.0f,
                            hoverColor
                    );
                }

                event.cancel();

                FontHelper.INSTANCE.drawCenteredString(
                        graphics,
                        widget.getMessage().getString(),
                        x + width / 2.0f,
                        y + height / 2.0f - 4.5f,
                        widget.active
                                ? activeTextColor
                                : inactiveTextColor
                );
            });

    @EventPointer
    private final EventListener<EventTick> eventTickEventListener =
            new EventListener<>(event -> {
                for (AbstractWidget widget : hoverProgress.keySet()) {
                    float progress = hoverProgress.getOrDefault(widget, 0.0f);

                    if (widget.active && widget.isHovered()) {
                        progress += shrinkSpeedProperty.value();
                    } else {
                        progress -= growSpeedProperty.value();
                    }

                    progress = Math.clamp(progress, 0.0f, 10.0f);
                    hoverProgress.put(widget, progress);
                }
            }, new TickFilter(EventTick.Mode.PRE));

    @EventPointer
    private final EventListener<EventSetScreen> eventSetScreenEventListener = new EventListener<>(event -> {hoverProgress.clear(); offsets.clear();});
}
