# Counter Class 
> a class for pots,counters,fryer
#### status: <span style="color:lightgreen;">done</span>
### <span style="color:cyan;">Contributors:</span>
<!--put your names here between the ``` if you worked on it, and put what you did-->
```diff 
haotian - created docs and some parts of class
Xinya - worked on class
```
### <span style="color:lightgreen;">Notes:</span>
```diff
will also be added as we work
```
### <span style="color:yellow;">Updates:</span>
```diff

```
### <span style="color:red;">Bugs:</span>
```diff

```
## <span style="color:green;">Attributes</span>

### **name** - private String
>The name of counter

### **ImagePath** - private String
>The Image It should Display


### **Interact** - private MouseEvent
>what happens when the player clicks on it

### **Holding** - private Holdable 
>what Object is currently on the Counter

### **ProcessedTime** - private double 
>how long has it been processed


## <span style="color:yellow;">Methods</span>

### **static newCounter(String Type)**-> Counter
>returns a new Counter Subclass from type

### **static newCounter(String Type,Holdable holding)**-> Counter
>returns a new Counter Subclass from type thats holding something

### **public SetHolding(Holdable obj)**-> void
>sets holding
### **public GetHolding()**-> Holdable
>returns the holding object

### **public GetImage()**-> string
>returns the image

### **public GetPercentage()**-> double[]
>returns what percentage is the item being cooked,chopped,etc in this format: double[processedTime,needed] 

### **public Update()** ->void
>subclass method

### **public CanPlace(Holdable obj)**-> boolean
>can the object be placed on the counter (subclass method) return true by default

### **LeftClick(Player player)**-> void
>What to do when Left clicked 

### **RightClick(Player player)**-> void
>What to do when Right clicked 

### **OnInteract(Player player,String key)**-> void
>What to do when the player left/right clicked 


## <span style="color:lightblue;">Subclasses</span>
> ## <span style="color:lightgreen;">Example</span>   
```java 
```