/*
 * Copyright (c) 2016 Marco Maccaferri and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marco Maccaferri - initial API and implementation
 */

package com.maccasoft.template;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Main extends ApplicationWindow {

    public static final String APP_TITLE = "SWT Application Template";
    public static final String APP_VERSION = "0.0.0";

    @SuppressWarnings("rawtypes")
    public static Image getImageFromResources(Class clazz, String name) {
        InputStream is = Main.class.getResourceAsStream(name);
        try {
            ImageLoader loader = new ImageLoader();
            ImageData[] data = loader.load(is);
            is.close();
            return new Image(Display.getDefault(), data[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Main() {
        super(null);
        setBlockOnOpen(true);
        addMenuBar();
        addStatusLine();
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);

        shell.setText(APP_TITLE);

        Image[] images = new Image[] {
            getImageFromResources(getClass(), "app64.png"), //
            getImageFromResources(getClass(), "app48.png"), //
            getImageFromResources(getClass(), "app32.png"), //
            getImageFromResources(getClass(), "app16.png"),
        };
        shell.setImages(images);
    }

    @Override
    protected void initializeBounds() {
        Rectangle screen = Display.getCurrent().getClientArea();
        Rectangle rect = new Rectangle(0, 0, 900, 900);
        rect.x = (screen.width - rect.width) / 2;
        rect.y = (screen.height - rect.height) / 2;
        if (rect.y < 0) {
            rect.height += rect.y * 2;
            rect.y = 0;
        }

        getShell().setLocation(rect.x, rect.y);
        getShell().setSize(rect.width, rect.height);
    }

    @Override
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();

        menuManager.add(createFileMenu());
        menuManager.add(createEditMenu());
        menuManager.add(createHelpMenu());

        return menuManager;
    }

    MenuManager createFileMenu() {
        MenuManager menuManager = new MenuManager("&File");

        Action action = new Action("New") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.SHIFT + SWT.ALT + 'N');
        menuManager.add(action);

        menuManager.add(new Separator());

        action = new Action("Open...") {

            @Override
            public void run() {
                FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
                String[] filterNames = new String[] {
                    "All Files (*.*)"
                };
                String[] filterExtensions = new String[] {
                    "*.*"
                };
                dlg.setFilterNames(filterNames);
                dlg.setFilterExtensions(filterExtensions);
                dlg.setText("Open File");
                dlg.open();
            }
        };
        menuManager.add(action);

        menuManager.add(new Separator());

        action = new Action("Save") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.CTRL + 'S');
        menuManager.add(action);

        action = new Action("Save As...") {

            @Override
            public void run() {
                FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
                String[] filterNames = new String[] {
                    "All Files (*.*)"
                };
                String[] filterExtensions = new String[] {
                    "*.*"
                };
                dlg.setFilterNames(filterNames);
                dlg.setFilterExtensions(filterExtensions);
                dlg.setText("Save File");
                dlg.open();
            }
        };
        menuManager.add(action);

        menuManager.add(new Separator());

        action = new Action("Exit") {

            @Override
            public void run() {
                getShell().close();
            }
        };
        menuManager.add(action);

        return menuManager;
    }

    MenuManager createEditMenu() {
        MenuManager menuManager = new MenuManager("&Edit");

        Action action = new Action("Cut") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.CTRL + 'X');
        menuManager.add(action);

        action = new Action("Copy") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.CTRL + 'C');
        menuManager.add(action);

        action = new Action("Paste") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.CTRL + 'V');
        menuManager.add(action);

        menuManager.add(new Separator());

        action = new Action("Select All") {

            @Override
            public void run() {

            }
        };
        action.setAccelerator(SWT.CTRL + 'A');
        menuManager.add(action);

        return menuManager;
    }

    MenuManager createHelpMenu() {
        MenuManager menuManager = new MenuManager("&Help");

        Action action = new Action("About " + APP_TITLE) {

            @Override
            public void run() {
                AboutDialog dlg = new AboutDialog(getShell());
                dlg.open();
            }
        };
        menuManager.add(action);

        return menuManager;
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        return composite;
    }

    static {
        System.setProperty("SWT_GTK3", "0");
    }

    public static void main(String[] args) {
        final Display display = new Display();
        Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {

            @Override
            public void run() {
                Main main = new Main();
                main.open();
            }
        });
        display.dispose();
    }
}
