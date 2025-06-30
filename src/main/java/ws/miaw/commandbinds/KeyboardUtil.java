package ws.miaw.commandbinds;

import org.lwjgl.input.Keyboard;

import java.util.*;

public class KeyboardUtil {

    private static final Map<Integer, String> keyMap = new HashMap<>();

    private static final Set<Integer> modifierKeys = new HashSet<>();

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

        modifierKeys.addAll(Arrays.asList(
                Keyboard.KEY_LCONTROL, Keyboard.KEY_RCONTROL,
                Keyboard.KEY_LSHIFT, Keyboard.KEY_RSHIFT,
                Keyboard.KEY_LMENU, Keyboard.KEY_RMENU,
                Keyboard.KEY_LMETA, Keyboard.KEY_RMETA
        ));
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
        // in the format <modifier key 1>, <key1>, <key2>, <key3>...
        // sorted alphabetically
        // e.g. Backspace, Right Ctrl, A, Enter

        Set<Integer> modifiers = new HashSet<>();
        Set<Integer> nonModifiers = new HashSet<>();
        keys.forEach(key -> {
            if(modifierKeys.contains(key)) {
                modifiers.add(key);
            } else nonModifiers.add(key);
        });

        StringBuilder modifierBuilder = new StringBuilder();
        modifiers.stream()
                .map(KeyboardUtil::getReadableKeyName)
                .sorted(Comparator.naturalOrder())
                .forEachOrdered(keyName -> {
                    modifierBuilder.append(keyName).append(", ");
                });

        StringBuilder nonModifierBuilder = new StringBuilder();
        nonModifiers.stream()
                .map(KeyboardUtil::getReadableKeyName)
                .sorted(Comparator.naturalOrder())
                .forEachOrdered(keyName -> {
                    nonModifierBuilder.append(keyName).append(", ");
                });

        if(modifiers.size() == 0) {
            return nonModifierBuilder.substring(0, nonModifierBuilder.length()-2).trim();
        }

        if(nonModifiers.size() == 0) {
            return modifierBuilder.substring(0, modifierBuilder.length()-2).trim();
        }

        return modifierBuilder.toString().trim() + " " + nonModifierBuilder.substring(0, nonModifierBuilder.length()-2).trim();
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
