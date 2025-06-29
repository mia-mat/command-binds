package ws.miaw.commandbinds;

import org.lwjgl.input.Keyboard;

import java.util.*;

public class KeyboardUtil {

    private static final Map<Integer, String> keyMap = new HashMap<>();

    static {
        keyMap.put(Keyboard.KEY_BACK, "Backspace");
        keyMap.put(Keyboard.KEY_LBRACKET, "Left Bracket");
        keyMap.put(Keyboard.KEY_RBRACKET, "Right Bracket");
        keyMap.put(Keyboard.KEY_RETURN, "Enter");
        keyMap.put(Keyboard.KEY_LCONTROL, "Left Ctrl");
        keyMap.put(Keyboard.KEY_RCONTROL, "Right Ctrl");
        keyMap.put(Keyboard.KEY_LSHIFT, "Left Shift");
        keyMap.put(Keyboard.KEY_RSHIFT, "Right Shift");
        keyMap.put(Keyboard.KEY_MULTIPLY, "*");
        keyMap.put(Keyboard.KEY_LMENU, "Left Alt");
        keyMap.put(Keyboard.KEY_RMENU, "Right Alt");
        keyMap.put(Keyboard.KEY_CAPITAL, "Caps Lock");
        keyMap.put(Keyboard.KEY_SCROLL, "Scroll Lock");
        keyMap.put(Keyboard.KEY_SUBTRACT, "-");
        keyMap.put(Keyboard.KEY_ADD, "+");
        keyMap.put(Keyboard.KEY_DECIMAL, ".");
        keyMap.put(Keyboard.KEY_AT, "@");
        keyMap.put(Keyboard.KEY_UNDERLINE, "_");
        keyMap.put(Keyboard.KEY_PRIOR, "Page Up");
        keyMap.put(Keyboard.KEY_NEXT, "Page Down");
        keyMap.put(Keyboard.KEY_LMETA, "Left Meta");
        keyMap.put(Keyboard.KEY_RMETA, "Right Meta");
    }

    // differs from the keyboard method in that it has human bindings for meta keys
    public static String getReadableKeyName(Integer key) {
        if(keyMap.containsKey(key)) return keyMap.get(key);
        char[] keyboardStringChars = Keyboard.getKeyName(key).toCharArray();
        StringBuilder formattedString = new StringBuilder();
        for(int i = 0; i < keyboardStringChars.length; i++) {
            if(i == 0) {
                formattedString.append(Character.toUpperCase(keyboardStringChars[0]));
            } else {

                // space between strings and digits (e.g. Numpad 0 rather than Numpad0)
                if(Character.isAlphabetic(keyboardStringChars[i-1]) && Character.isDigit(keyboardStringChars[i])) {
                    formattedString.append(" ");
                }
                formattedString.append(Character.toLowerCase(keyboardStringChars[i]));
            }
        }

        return formattedString.toString().trim();
    }

    public static String getBindString(Set<Integer> keys) {
        // in the format <key1>, <key2>, <key3>...
        // sorted alphabetically
        // e.g. Backspace, Enter, Right Ctrl

        StringBuilder stringBuilder = new StringBuilder();

        keys.stream()
                .map(KeyboardUtil::getReadableKeyName)
                .sorted(Comparator.naturalOrder())
                .forEachOrdered(keyName -> {
                    stringBuilder.append(keyName).append(", ");
                });

        return stringBuilder.substring(0, stringBuilder.length()-2).trim();
    }

    public static Set<Integer> getCurrentlyPressedKeys() {
        Set<Integer> pressedKeys = new HashSet<>();

        for (int keyCode = 0; keyCode < Keyboard.KEYBOARD_SIZE; keyCode++) {
            if (Keyboard.isKeyDown(keyCode)) {
                pressedKeys.add(keyCode);
            }
        }

        return pressedKeys;
    }


}
