package calendar.input;

// determines what Input should do after the InputLayer handles the key
// return:
//   LayerChange.keep() to keep the current layer,
//   LayerChange.exit() to exit the current layer,
//   LayerChange.switchTo(new) to switch to a new layer
public class LayerChange {
    private boolean exit;
    private InputLayer newLayer;

    private LayerChange(boolean exit, InputLayer newLayer) {
        this.exit = exit;
        this.newLayer = newLayer;
    }

    public static LayerChange keep() { return new LayerChange(false, null); }
    public static LayerChange exit() { return new LayerChange(true, null); }
    public static LayerChange switchTo(InputLayer newLayer) {
        return new LayerChange(false, newLayer);
    }

    public boolean exits() { return this.exit; }
    public InputLayer newLayer() { return this.newLayer; }
}
