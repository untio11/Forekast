import controlP5.*;

ControlP5 gui;

//Interface
//PImage background;
PImage homescreen1;
PImage homescreen2;
PImage homescreen3;
PImage homescreen4;
PImage wardrobe1;
PImage tshirt;
PImage jeans;
PImage home;
PImage editshirt;
PImage editjeans;
PImage settings;
PImage camera;
PFont f;
int pageNumber;

void setup() {
  size(360, 740); // Window Size
  background(0, 0, 0); // Background Colour

  pageNumber = 1;
  //font
  f = createFont("Gadugi", 50, true); // Gadugi, 50 point, anti-aliasing on

  // Images
  homescreen1 = loadImage("Homescreen1.jpg");
  homescreen2 = loadImage("Homescreen2.jpg");
  homescreen3 = loadImage("Homescreen3.jpg");
  homescreen4 = loadImage("Homescreen4.jpg");
  home = loadImage("Burger_Menu.jpg");
  wardrobe1 = loadImage("Wardrobe_1.jpg");
  tshirt = loadImage("TShirts.jpg");
  jeans = loadImage("Jeans.jpg");
  editshirt = loadImage("EditShirt.png");
  editjeans = loadImage("EditJeans.jpg");
  settings = loadImage("Settings.jpg");
  camera = loadImage("Camera.jpg");
  
  // Setup
  gui = new ControlP5(this);

  // Tabs    
  
    // Tabs        
  gui.addTab("Homescreen1")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(1) // 1 is taken by default
    .activateEvent(true)
    .setActive(true)
  ;

  gui.addTab("Homescreen2")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(2) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen3")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(3) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen4")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(4) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Wardrobe1")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(5) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Tshirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(6) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Jeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(7) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("EditShirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(8) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("EditJeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(9) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Settings")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(10) // 1 is taken by default
    .activateEvent(true)
    ;
 
  gui.addTab("Home")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(11) // 1 is taken by default
    .activateEvent(true)
  ;
  
  gui.addTab("CameraJeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(12) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("CameraShirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(13) // 1 is taken by default
    .activateEvent(true)
    ;

  // Setup Tabs
  gui.getTab("default").remove();

  // Buttons screen 1
  for (int i = 1; i <= 10; i++) {
    gui.addButton("home" + i)
      .setPosition(0, 20) // Position of the button
      .setSize(60, 50) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          gui.getTab("Home").bringToFront();
          pageNumber = 11;
        }
      }
    }
    )
    ;
  }

  gui.addButton("refreshAll1")
    .setPosition(5, 70) // Position of the button
    .setSize(40, 40) // Size of the button
    .setLabelVisible(false)
    .setColorBackground(color(255, 255, 255, 1)) // Button colour
    .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
    .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
    .addCallback( // Listener & ActionPerformed in one
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
        gui.getTab("Homescreen4").bringToFront();
        pageNumber = 4;
      }
    }
  }
  )
  ;

  // Buttons screen 2


  gui.addButton("refreshAll2")
    .setPosition(5, 70) // Position of the button
    .setSize(40, 40) // Size of the button
    .setLabelVisible(false)
    .setColorBackground(color(255, 255, 255, 1)) // Button colour
    .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
    .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
    .addCallback( // Listener & ActionPerformed in one
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
        gui.getTab("Homescreen3").bringToFront();
        pageNumber = 3;
      }
    }
  }
  )
  ;

  // Buttons screen 3
  gui.addButton("refreshAll3")
    .setPosition(5, 70) // Position of the button
    .setSize(40, 40) // Size of the button
    .setLabelVisible(false)
    .setColorBackground(color(255, 255, 255, 1)) // Button colour
    .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
    .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
    .addCallback( // Listener & ActionPerformed in one
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
        gui.getTab("Homescreen2").bringToFront();
        pageNumber = 2;
      }
    }
  }
  )
  ;

  // Buttons screen 4

  gui.addButton("refreshAll4")
    .setPosition(5, 70) // Position of the button
    .setSize(40, 40) // Size of the button
    .setLabelVisible(false)
    .setColorBackground(color(255, 255, 255, 1)) // Button colour
    .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
    .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
    .addCallback( // Listener & ActionPerformed in one
    new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch(theEvent.getAction()) {
        case(ControlP5.ACTION_PRESSED):
        gui.getTab("Homescreen1").bringToFront();
        pageNumber = 1;
      }
    }
  }
  )
  ;

  for (int i = 1; i <= 4; i++) {
    gui.addButton("shirtRefreshLeft" + i)
      .setPosition(10, 295) // Position of the button
      .setSize(40, 40) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = (pageNumber + 2) % 4;
          if (pageNumber == 0) {
            pageNumber = 4;
          }
          gui.getTab("Homescreen" + pageNumber).bringToFront();
        }
      }
    }
    )
    ;

    gui.addButton("shirtRefreshRight" + i)
      .setPosition(315, 295) // Position of the button
      .setSize(40, 40) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = (pageNumber + 2) % 4;
          if (pageNumber == 0) {
            pageNumber = 4;
          }
          gui.getTab("Homescreen" + pageNumber).bringToFront();
        }
      }
    }

    )
    ;

    gui.addButton("bottomRefreshLeft" + i)
      .setPosition(73, 435) // Position of the button
      .setSize(40, 40) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          if (pageNumber == 1 || pageNumber == 3) {
            pageNumber++;
          } else {
            pageNumber--;
          }
          gui.getTab("Homescreen" + pageNumber).bringToFront();
        }
      }
    }
    )
    ;

    gui.addButton("bottomRefreshRight" + i)
      .setPosition(245, 435) // Position of the button
      .setSize(40, 40) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          if (pageNumber == 1 || pageNumber == 3) {
            pageNumber++;
          } else {
            pageNumber--;
          }
          gui.getTab("Homescreen" + pageNumber).bringToFront();
        }
      }
    }

    )
    ;
  }
  
  // Buttons for wardrobe 1 
  gui.addButton("shirts")
      .setPosition(8, 135) // Position of the button
      .setSize(100, 130) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      //.setColorLabel(color(0, 0, 0)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 6;
          gui.getTab("Tshirt").bringToFront();
        }
      }
    }

    )
    ;
    
      gui.addButton("jeans")
      .setPosition(235, 420) // Position of the button
      .setSize(100, 130) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      //.setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 7;
          gui.getTab("Jeans").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("wardrobe")
      .setPosition(10, 150) // Position of the button
      .setSize(270, 65) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 5;
          gui.getTab("Wardrobe1").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("homescreen")
      .setPosition(10, 80) // Position of the button
      .setSize(270, 65) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 1;
          gui.getTab("Homescreen1").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("settings")
      .setPosition(10, 220) // Position of the button
      .setSize(270, 65) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 10;
          gui.getTab("Settings").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("continueJeans")
      .setPosition(0, 0) // Position of the button
      .setSize(360, 740) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 9;
          gui.getTab("EditJeans").bringToFront();
        }
      }
    }
    )
    ;
    
    gui.addButton("continueShirt")
      .setPosition(0, 0) // Position of the button
      .setSize(360, 740) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 8;
          gui.getTab("EditShirt").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("addShirt")
      .setPosition(28, 147) // Position of the button
      .setSize(89, 137) // Size of the button
      .setLabelVisible(false)
      .setColorBackground(color(255, 255, 255, 1)) // Button colour
      .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 13;
          gui.getTab("CameraShirt").bringToFront();
        }
      }
    }

    )
    ;
    
    gui.addButton("addJeans")
      .setPosition(28, 147) // Position of the button
      .setSize(89, 137) // Size of the button
      .setLabelVisible(false)
      //.setColorBackground(color(255, 255, 255, 1)) // Button colour
      //.setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 12;
          gui.getTab("CameraJeans").bringToFront();
        }
      }
    }

    )
    ;

  // Locations of the buttons - the "start" button is in the "default"
  for (int i = 1; i <= 4; i++) {
    gui.getController("home" + i).moveTo("Homescreen" + i);
    gui.getController("refreshAll" + i).moveTo("Homescreen" + i);
    gui.getController("shirtRefreshLeft" + i).moveTo("Homescreen" + i);
    gui.getController("shirtRefreshRight" + i).moveTo("Homescreen" + i);
    gui.getController("bottomRefreshLeft" + i).moveTo("Homescreen" + i);
    gui.getController("bottomRefreshRight" + i).moveTo("Homescreen" + i);
  }
    gui.getController("shirts").moveTo("Wardrobe1");
    gui.getController("jeans").moveTo("Wardrobe1");
    gui.getController("wardrobe").moveTo("Home");
    gui.getController("homescreen").moveTo("Home");
    gui.getController("settings").moveTo("Home");
    gui.getController("home" + 5).moveTo("Wardrobe1");
    gui.getController("home" + 6).moveTo("Tshirt");
    gui.getController("home" + 7).moveTo("Jeans");
    gui.getController("home" + 8).moveTo("EditShirt");
    gui.getController("home" + 9).moveTo("EditJeans");
    gui.getController("home" + 10).moveTo("Settings");
    gui.getController("continueJeans").moveTo("CameraJeans");
    gui.getController("continueShirt").moveTo("CameraShirt");
    gui.getController("addShirt").moveTo("Tshirt");
    gui.getController("addJeans").moveTo("Jeans");
}

void controlEvent(ControlEvent theControlEvent) { // For debugging
  if (theControlEvent.isTab()) {
    println("got an event from tab : "+theControlEvent.getTab().getName()+" with id "+theControlEvent.getTab().getId());
  }
}

void draw() {
  switch (pageNumber) {
  case 1: 
    image(homescreen1, 0, 0);
    break;
  case 2: 
    image(homescreen2, 0, 0);
    break;
  case 3: 
    image(homescreen3, 0, 0);
    break;
  case 4: 
    image(homescreen4, 0, 0);
    break;
  case 5:
    image(wardrobe1, 0, 0);
    break;
  case 6:
    image(tshirt, 0, 0);
    break;
  case 7:
    image(jeans, 0, 0);
    break;
  case 8:
    image(editshirt, 0, 0);
    break;
  case 9:
    image(editjeans, 0, 0);
    break;
  case 10:
    image(settings, 0, 0);
    break;
  case 11:
    image(home, 0, 0);
    break;
  case 12:
    image(camera, 0, 0);
    break;
  case 13:
    image(camera, 0, 0);
  }
}

void keyPressed() {
  // Use this if you want to access keyboard keys (probably won't)
}
