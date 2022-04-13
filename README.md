# Required Features

### Sharpen Filter
**Contributors:** Riley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-J)

**Testing:**

**Known Issues:** No known issues

---

### Gaussian Blur Filter
**Contributors:** Riley, Will, Jet

**Access:** Menu, Keyboard Shortcut (Ctrl-G)

**Testing:** JUnit testing to ensure 2d guassian filter calculations are correct.

**Known Issues:** No known issues

---

### Median Filter
**Contributors:** Riley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-D)

**Testing:** JUnit testing to ensure median calculations are done correctly.

**Known Issues:** No known issues

---

### Brightness Adjustment
**Contributors:** Jet, Arlo

**Access:** Menu, Keyboard Shortcut (Ctrl-B)

**Testing:**

**Known Issues:**

---

### Contrast Adjustment
**Contributors:** Jet, Arlo

**Access:** Menu, Keyboard Shortcut (Ctrl-C)

**Testing:**

**Known Issues:**

---

### Image Resize
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-Comma, Ctrl-Period)

**Testing:**Ensured that images dont lose edges when downsizing, and dont have black
borders when caling up. Tested with images with different width/height ratios

**Known Issues:** Scaling out too much will result in being unable to zoom in to a reasonable size, Scaling out many times then attempting Scale up will result in a blurry image, 

---

### Image Rotations
**Contributors:** Bradley, 

**Access:** Menu, Keyboard Shortcut (Ctrl-L, Ctrl-R)

**Testing:** Ensured that images with different width/height ratios dont get 
cropped or muddled up during the rotations. Ensured that doing multiple rotations
and flips in a row does not ruin the image or cause unexpected reults.

**Known Issues:** No known issues

---

### Image Flip
**Contributors:** Bradley

**Access:** Menu, Keyboard Shortcut (Ctrl-H, Ctrl-V)

**Testing:** Ensured that images with different width/height ratios dont get 
cropped or muddled up during the flip. Ensured that doing multiple rotations
and flips in a row does not ruin the image or cause unexpected reults.

**Known Issues:** No known issues

---

### Image Export
**Contributors:** Arlo, 

**Access:** Menu, Keyboard Shortcut (Ctrl-E)

**Testing:**

**Known Issues:**

---

### Toolbar
**Contributors:** Arlo, 

**Testing:**

**Known Issues:**

**Brief Discussion:** Zoom in and out, rotate 90 degrees, flip vertically and horizontally, undo and redo. These features were deemed the most useful and needed to be more promenant within Andie

---

### Keyboard Shortcuts
**Contributors:** Arlo, 

**Testing:**

**Known Issues:**

**Brief Discussion:** Every feature ended up having a shortcut. A help menu was added to show the list of shortcuts to help the user find them.

---

### Exception Handling/Other Error Avoidance
**Contributors:** Will

**Testing:** On going testing attempting to break the program, no JUnit testing however due do difficulty automating the process

**General Errors Caught:**

---

# Other Features

### Continuous Integration Testing & Javadoc
**Contributors:** Riley

**Brief Discussion:** Created JUnit testing and added in depth Javadoc commenting to all code, and added JUnit testing and Javadoc documentation to be created to the on git push into the continuous Integration pipeline.