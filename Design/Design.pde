import controlP5.*;

ControlP5 gui;
ControlP5 gui2;

//Interface
//PImage background;
PImage homescreen1;
PImage homescreen2;
PImage homescreen3;
PImage homescreen4;
PImage wardrobe1;
PImage tshirt;
PImage jeans;
PImage burgerMenu;
PImage editshirt;
PImage editjeans;
PImage settings;
PImage camera;
PFont f;
int pageNumber;
int myColor = color(255, 0, 0);
int sliderValue = 100;
int sliderTicks1 = 100;

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
  burgerMenu = loadImage("Burger_Menu.jpg");
  wardrobe1 = loadImage("Wardrobe_1.jpg");
  tshirt = loadImage("TShirts.jpg");
  jeans = loadImage("Jeans.png");
  editshirt = loadImage("EditShirt.png");
  editjeans = loadImage("EditJeans.png");
  settings = loadImage("Settings.png");
  camera = loadImage("Camera2.jpg");
  // Setup
  gui = new ControlP5(this);
  gui2 = new ControlP5(this);
  gui2.setAutoDraw(false);

  // Tabs      
  gui.addTab("Homescreen1")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(2) // 1 is taken by default
    .activateEvent(true)
    .setActive(true)
    ;

  gui.addTab("Homescreen2")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(3) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen3")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(4) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen4")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(5) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Wardrobe1")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(6) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Tshirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(7) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Jeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(8) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("EditShirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(9) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("EditJeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(10) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Settings")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(11) // 1 is taken by default
    .activateEvent(true)
    ;
   
  gui.addTab("BM")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(12) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("CameraJeans")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(13) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("CameraShirt")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(14) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui2.addTab("heart")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(15) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui2.addTab("heart2")
    .setColorBackground(color(0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(0)) // Activates the tab colour?
    .setId(16) // 1 is taken by default
    .activateEvent(true)
    ;
    
  // Setup Tabs
  gui.getTab("default").remove();
  
  gui.getTab("Homescreen1")
    .activateEvent(true)
    .setLabel("Homescreen1")
    .setId(2)
    ;
     
  gui.getTab("Homescreen2")
    .activateEvent(true)
    .setLabel("Homescreen2")
    .setId(3)
    ;
     
  gui.getTab("Homescreen3")
    .activateEvent(true)
    .setLabel("Homescreen3")
    .setId(4)
    ;
    
  gui.getTab("Homescreen4")
    .activateEvent(true)
    .setLabel("Homescreen4")
    .setId(5)
    ;
     
  gui.getTab("Wardrobe1")
    .activateEvent(true)
    .setLabel("Wardrobe1")
    .setId(6)
    ;
     
  gui.getTab("Tshirt")
    .activateEvent(true)
    .setLabel("Tshirt")
    .setId(7)
    ;
     
  gui.getTab("Jeans")
    .activateEvent(true)
    .setLabel("Jeans")
    .setId(8)
    ;
     
  gui.getTab("EditShirt")
    .activateEvent(true)
    .setLabel("EditShirt")
    .setId(9)
    ;
    
  gui.getTab("EditJeans")
    .activateEvent(true)
    .setLabel("EditJeans")
    .setId(10)
    ;
    
  gui.getTab("Settings")
    .activateEvent(true)
    .setLabel("Settings")
    .setId(11)
    ;
     
  gui.getTab("BM")  
    .activateEvent(true)
    .setLabel("BM")
    .setId(12)
    ;
     
  gui.getTab("CameraJeans")
    .activateEvent(true)
    .setLabel("CameraJeans")
    .setId(13)
    ;
     
  gui.getTab("CameraShirt")
    .activateEvent(true)
    .setLabel("CameraShirt")
    .setId(14)
    ;
    
  gui.getTab("heart")
    .activateEvent(true)
    .setLabel("heart")
    .setId(15)
    ;
    
  gui.getTab("heart2")
    .activateEvent(true)
    .setLabel("heart2")
    .setId(16)
    ;
     
     
  /* Refresh Buttons */
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
    );
    
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
    );
    
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
    );

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
    );

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
    );

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
      );

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
      );

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
      );
  }
  /*End for-loop*/
  
  /* Buttons for Burger Menu */ 
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
      );
      
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
    );
    
    
    
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
    );
  
  /* Buttons for Wardrobe */
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
    );
    
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
    );
    
    // BurgerMenu buttons (for each page they're on)
    for (int i = 1; i <= 10; i++) {
      gui.addButton("burgerMenu" + i)
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
                gui.getTab("BM").bringToFront();
                pageNumber = 11;
            }
          }
        }
        );
    }
    
    gui.addButton("editShirt")
    .setPosition(width/4 - 6, height/2 + 58) // Position of the button
    .setSize(20, 20) // Size of the button
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
    );
    
    gui.addButton("editJeans")
    .setPosition(width/2, 270) // Position of the button
    .setSize(20, 20) // Size of the button
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
    );
    
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
    );
    
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
    );
    
    gui.addButton("addJeans")
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
          pageNumber = 12;
          gui.getTab("CameraJeans").bringToFront();
        }
      }
    }
    );
    
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
    );
    
    
      
    // Back Buttons: 
    for (int i = 1; i <= 5; i++) {
      gui.addButton("back" + i)
        .setPosition(10, 80) // Position of the button
        .setSize(25, 25) // Size of the button
        .setLabelVisible(false)
        .setColorBackground(color(255, 255, 255, 1)) // Button colour
        .setColorForeground(color(255, 255, 255, 1)) // Hover button colour
        .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
        .addCallback( // Listener & ActionPerformed in one
        new CallbackListener() {
          public void controlEvent(CallbackEvent theEvent) {
            switch(theEvent.getAction()) {
              case(ControlP5.ACTION_PRESSED):
              if (pageNumber == 9) { //Edit Jeans --> back to Jeans
                pageNumber = 7;
                gui.getTab("Jeans").bringToFront();
              }
              else if (pageNumber == 6 || pageNumber == 7  ) { // Jeans/Tshirts to Wardrobe
                pageNumber = 5;
                gui.getTab("Wardrobe1").bringToFront();
              }
              else if (pageNumber == 8) { // Edit Tshirt --> back to Tshirts
                pageNumber = 6;
                gui.getTab("Tshirt").bringToFront();
              }
            }
          }
        }
        );
      }
    
    // Add Sliders
    gui2.addSlider("HeartJeans")
             .setRange(0, 255)
             .setValue(128)
             .setLabelVisible(false)
             .setColorBackground(color(0))
             .setColorForeground(color(255, 0, 0)) 
             .setColorActive(color(255, 0, 0))
             .setPosition(width - 70, 300)
             .setSize(40, 40)
             ;
             
    gui2.addSlider("HeartTshirt")
             .setRange(0, 255)
             .setValue(128)
             .setLabelVisible(false)
             .setColorBackground(color(0))
             .setColorForeground(color(255, 0, 0)) 
             .setColorActive(color(255, 0, 0))
             .setPosition(width - 70, 300)
             .setSize(40, 40)
             ;

  /* Locations of the buttons */
  // Buttons for each of the homescreens
  for (int i = 1; i <= 4; i++) {
    gui.getController("burgerMenu" + i).moveTo("Homescreen" + i);
    gui.getController("refreshAll" + i).moveTo("Homescreen" + i);
    gui.getController("shirtRefreshLeft" + i).moveTo("Homescreen" + i);
    gui.getController("shirtRefreshRight" + i).moveTo("Homescreen" + i);
    gui.getController("bottomRefreshLeft" + i).moveTo("Homescreen" + i);
    gui.getController("bottomRefreshRight" + i).moveTo("Homescreen" + i);
  }
  
  // Everything else
  gui.getController("shirts").moveTo("Wardrobe1");
  gui.getController("jeans").moveTo("Wardrobe1");
  
  gui.getController("wardrobe").moveTo("BM");
  gui.getController("homescreen").moveTo("BM");
  gui.getController("settings").moveTo("BM");
  
  gui.getController("burgerMenu" + 5).moveTo("Wardrobe1");
  gui.getController("burgerMenu" + 6).moveTo("Tshirt");
  gui.getController("burgerMenu" + 7).moveTo("Jeans");
  gui.getController("burgerMenu" + 8).moveTo("EditShirt");
  gui.getController("burgerMenu" + 9).moveTo("EditJeans");
  gui.getController("burgerMenu" + 10).moveTo("Settings");

  gui.getController("back" + 1).moveTo("Tshirt");
  gui.getController("back" + 2).moveTo("Jeans");
  gui.getController("back" + 3).moveTo("EditShirt");
  gui.getController("back" + 4).moveTo("EditJeans");

  gui.getController("continueJeans").moveTo("CameraJeans");
  gui.getController("continueShirt").moveTo("CameraShirt");
  
  gui.getController("addShirt").moveTo("Tshirt");
  gui.getController("addJeans").moveTo("Jeans");
  gui.getController("editShirt").moveTo("Tshirt");
  gui.getController("editJeans").moveTo("Jeans");
   
  gui2.getController("HeartTshirt").moveTo("heart2");
  gui2.getController("HeartJeans").moveTo("heart"); 
}

void controlEvent(ControlEvent theControlEvent) { // For debugging
  if (theControlEvent.isTab()) {
    println("got an event from tab : "+theControlEvent.getTab().getName()+" with id "+theControlEvent.getTab().getId());
  }
}

void draw() {
  background(0);
  switch (pageNumber) {
    case 1: // Homescreen1
      image(homescreen1, 0, 0);
      break;
    case 2: // Homescreen2
      image(homescreen2, 0, 0);
      break;
    case 3: // Homescreen3
      image(homescreen3, 0, 0);
      break;
    case 4: // Homescreen4
      image(homescreen4, 0, 0);
      break;
    case 5: // Wardrobe1
      image(wardrobe1, 0, 0);
      break;
    case 6: // Tshirt
      image(tshirt, 0, 0);
      tshirt.resize(width, height);
      break;
    case 7: // Jeans
      image(jeans, 0, 0);
      jeans.resize(width, height);
      break;
    case 8: // EditShirt
      gui2.getTab("heart2").bringToFront();
      gui2.draw();
      image(editshirt, 0, 0);
      editshirt.resize(width, height);
      break;
    case 9: // EditJeans
      gui2.getTab("heart").bringToFront();
      gui2.draw();
      image(editjeans, 0, 0);
      editjeans.resize(width, height);
      break;
    case 10: // Settings
      image(settings, 0, 0);
      settings.resize(width, height);
      break;
    case 11: // Burger Menu
      image(burgerMenu, 0, 0);
      break;
    case 12: // CameraJeans
      image(camera, 0, 0);
      camera.resize(width, height);
      break;
    case 13: // CameraShirt
      image(camera, 0, 0);
      camera.resize(width, height);
   }
}
