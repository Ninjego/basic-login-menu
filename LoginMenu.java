package vibe.ui.menu;

import java.awt.Color;
import java.io.IOException;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import vibe.ui.CustomTextField;
import vibe.utils.SessionChanger;
import vibe.utils.gui.GuiUtils;

public class LoginMenu extends GuiScreen {

	protected CustomTextField mailInput;
	protected CustomTextField passInput;
	
	@Override
	public void initGui() {
		ScaledResolution sr = new ScaledResolution(mc);
		mailInput = new CustomTextField(101, mc.fontRendererObj, sr.getScaledWidth() / 2 - 75 + 40, sr.getScaledHeight() / 2 - 35, 100, 15);
		mailInput.setMaxStringLength(100);
		passInput = new CustomTextField(101, mc.fontRendererObj, sr.getScaledWidth() / 2 - 75 + 40, sr.getScaledHeight() / 2 - 15, 100, 15);
		this.buttonList.add(new GuiButton(1, sr.getScaledWidth() / 2 - 80 / 2 - 12, sr.getScaledHeight() / 2 + 15, 100, 15, "Login"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		ScaledResolution sr = new ScaledResolution(mc);
		GuiUtils.drawRoundedRect(sr.getScaledWidth() / 2 - 80, sr.getScaledHeight() / 2 - 40, sr.getScaledWidth() / 2 + 80, sr.getScaledHeight() / 2 + 35, 1, 0xf90d0d0d);
		mc.fontRendererObj.drawString("Mail:", sr.getScaledWidth() / 2 - 75 + 10, sr.getScaledHeight() / 2 - 40 + mc.fontRendererObj.FONT_HEIGHT, -1);
		mc.fontRendererObj.drawString("Pass:", sr.getScaledWidth() / 2 - 75 + 10, sr.getScaledHeight() / 2 - 20 + mc.fontRendererObj.FONT_HEIGHT, -1);
		mailInput.drawTextBox();
		passInput.drawTextBox();
		
		String strText = "Logged in as: " + EnumChatFormatting.GREEN + mc.getSession().getUsername();
		mc.fontRendererObj.drawString(strText, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(strText) / 2, 5, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		mailInput.textboxKeyTyped(typedChar, keyCode);
		passInput.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		ScaledResolution sr = new ScaledResolution(mc);
		if(mouseY > sr.getScaledHeight() / 2 + 15 && mouseY < sr.getScaledHeight() / 2 + 30 && mouseX > sr.getScaledWidth() / 2 - 80 / 2 - 12 && mouseX < sr.getScaledWidth() / 2 - 80 / 2 - 12 + 100) {
			if(!mailInput.getText().isEmpty()) {

				if(passInput.getText().isEmpty()) {
					SessionChanger.getInstance().setUserOffline(mailInput.getText());
				} else {
					SessionChanger.getInstance().setUser(mailInput.getText(), passInput.getText());					
				}

			}
		}
		mailInput.mouseClicked(mouseX, mouseY, mouseButton);
		passInput.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void updateScreen() {
		mailInput.updateCursorCounter();
		passInput.updateCursorCounter();
	}
	
}
