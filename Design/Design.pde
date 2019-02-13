import controlP5.*;

ControlP5 gui;

//Interface
//PImage background;
PImage homescreen1;
PImage homescreen2;
PImage homescreen3;
PImage homescreen4;
PImage wardrobe1;
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
  wardrobe1 = loadImage("Wardrobe_1.jpg");
  // Setup
  gui = new ControlP5(this);

  // Tabs      
  gui.addTab("Homescreen1")
    .setColorBackground(color(255, 210, 0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(255, 128, 0)) // Activates the tab colour?
    .setId(1) // 1 is taken by default
    .activateEvent(true)
    .setActive(true);
  ;

  gui.addTab("Homescreen2")
    .setColorBackground(color(255, 210, 0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(255, 128, 0)) // Activates the tab colour?
    .setId(2) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen3")
    .setColorBackground(color(255, 210, 0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(255, 128, 0)) // Activates the tab colour?
    .setId(3) // 1 is taken by default
    .activateEvent(true)
    ;

  gui.addTab("Homescreen4")
    .setColorBackground(color(255, 210, 0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(255, 128, 0)) // Activates the tab colour?
    .setId(4) // 1 is taken by default
    .activateEvent(true)
    ;
    
  gui.addTab("Wardrobe1")
    .setColorBackground(color(255, 210, 0)) // Colour tab in top left
    .setColorLabel(color(255)) // Colour of the text in the tab
    .setColorActive(color(255, 128, 0)) // Activates the tab colour?
    .setId(5) // 1 is taken by default
    .activateEvent(true)
    ;

  // Setup Tabs
  gui.getTab("default").remove();

  // Buttons screen 1
  for (int i = 1; i <= 4; i++) {
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
          gui.getTab("Wardrobe1").bringToFront();
          pageNumber = 5;
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
      .setPosition(245, 435) // Position of the button
      .setSize(40, 40) // Size of the button
      .setLabelVisible(false)
      //.setColorBackground(color(255, 255, 255, 1)) // Button colour
      //.setColorForeground(color(255, 255, 255, 1)) // Hover button colour
      .setValue(1) // Num of values, more used in sliders so buttons only have 1 possible value
      .addCallback( // Listener & ActionPerformed in one
      new CallbackListener() {
      public void controlEvent(CallbackEvent theEvent) {
        switch(theEvent.getAction()) {
          case(ControlP5.ACTION_PRESSED):
          pageNumber = 6;
          gui.getTab("Homescreen" + pageNumber).bringToFront();
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
  }
}

void keyPressed() {
  // Use this if you want to access keyboard keys (probably won't)
}
