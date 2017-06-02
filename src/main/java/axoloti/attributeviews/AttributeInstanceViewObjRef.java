package axoloti.attributeviews;

import axoloti.attribute.AttributeInstanceController;
import axoloti.attribute.AttributeInstanceObjRef;
import axoloti.objectviews.IAxoObjectInstanceView;
import axoloti.utils.Constants;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class AttributeInstanceViewObjRef extends AttributeInstanceViewString {

    JTextField TFObjName;
    JLabel vlabel;

    public AttributeInstanceViewObjRef(AttributeInstanceObjRef attributeInstance, AttributeInstanceController controller, IAxoObjectInstanceView axoObjectInstanceView) {
        super(attributeInstance, controller, axoObjectInstanceView);
    }

    @Override
    public AttributeInstanceObjRef getAttributeInstance() {
        return (AttributeInstanceObjRef) super.getAttributeInstance();
    }

    public void PostConstructor() {
        super.PostConstructor();
        TFObjName = new JTextField(getAttributeInstance().getString());
        Dimension d = TFObjName.getSize();
        d.width = 92;
        d.height = 22;
        TFObjName.setFont(Constants.FONT);
        TFObjName.setMaximumSize(d);
        TFObjName.setMinimumSize(d);
        TFObjName.setPreferredSize(d);
        TFObjName.setSize(d);
        add(TFObjName);
        TFObjName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }
        });
        TFObjName.getDocument().addDocumentListener(new DocumentListener() {

            void update() {
                getAttributeInstance().setString(TFObjName.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });
        TFObjName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                getAttributeInstance().setValueBeforeAdjustment(TFObjName.getText());
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!TFObjName.getText().equals(getAttributeInstance().getValueBeforeAdjustment())) {
                    attributeInstance.getObjectInstance().getPatchModel().setDirty();
                }
            }
        });
    }

    public void Lock() {
        if (TFObjName != null) {
            TFObjName.setEnabled(false);
        }
    }

    @Override
    public void UnLock() {
        if (TFObjName != null) {
            TFObjName.setEnabled(true);
        }
    }

    @Override
    public String getString() {
        return getAttributeInstance().getString();
    }

    @Override
    public void setString(String objName) {
        getAttributeInstance().setString(objName);
        if (TFObjName != null) {
            TFObjName.setText(objName);
        }
    }
}
