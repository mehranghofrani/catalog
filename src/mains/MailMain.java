/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mains;

import uiComponents.pages.EntrancePage;
import uiComponents.pages.MainFrame;

import javax.swing.*;

public class MailMain
{
    public static void main(String[] args)

    {
//        DBManager.getMyInstance();
//        new CatalogEmailSendingPage();
//          ImageCapturingPage.getInstance();
//        MainFrame.getInstance().showPanel(new EntrancePage(MainFrame.getInstance().getSize()).getPanelIndex());
        MainFrame.getInstance().showPanel(new EntrancePage().getPanelIndex());

    }

}
