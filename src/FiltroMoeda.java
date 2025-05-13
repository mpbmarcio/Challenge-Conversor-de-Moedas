import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.text.DecimalFormat;

public class FiltroMoeda extends DocumentFilter{
    private final DecimalFormat format = new DecimalFormat("##0.00");

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String existingText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = existingText.substring(0, offset) + text + existingText.substring(offset + length);

        if (newText.matches("\\d*(\\.\\d{0,2})?")) { // Permite apenas números e no formato correto
            fb.replace(offset, length, text, attrs);
        }
    }
}
