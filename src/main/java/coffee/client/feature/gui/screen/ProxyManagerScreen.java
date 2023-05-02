/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors.
 * Some rights reserved, refer to LICENSE file.
 */

package coffee.client.feature.gui.screen;

import coffee.client.feature.gui.element.impl.ButtonGroupElement;
import coffee.client.feature.gui.element.impl.FlexLayoutElement;
import coffee.client.feature.gui.element.impl.TextElement;
import coffee.client.feature.gui.element.impl.TextFieldElement;
import coffee.client.feature.gui.screen.base.CenterOverlayScreen;
import coffee.client.helper.font.FontRenderers;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class ProxyManagerScreen extends CenterOverlayScreen {
    public static Proxy currentProxy = null;
    TextFieldElement ip;
    TextFieldElement port;
    boolean isSocks4 = true;

    public ProxyManagerScreen(Screen p) {
        super(p, "Proxy manager", "Manage your proxy connection");
    }

    boolean canApply() {
        String currentIp = this.ip.get();
        if (currentIp.isEmpty()) {
            return false;
        }
        String currentPort = this.port.get();
        try {
            int port = Integer.parseInt(currentPort);
            if (port < 0 || port > 0xFFFF) {
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    protected void initInternal() {
    }

    @Override
    public void renderInternal(MatrixStack stack, int mouseX, int mouseY, float delta) {
        //Renderer.R2D.renderQuad(stack, Color.WHITE, 0, 0, width, height);
        super.renderInternal(stack, mouseX, mouseY, delta);
    }

    public record Proxy(String address, int port, boolean socks4, String user, String pass) {

    }

}
