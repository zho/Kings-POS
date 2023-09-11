package com.openbravo.pos.reports.kings;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JParamsDebtCash2 extends javax.swing.JPanel implements ReportEditorCreator {

    private JComboBox comboDebtCash;
    private JLabel lblDebtCash;

    public JParamsDebtCash2() {

        initComponents();

        lblDebtCash.setText("BON / TUNAI");

        String[] selectedValue = {"bon", "tunai"};

        ComboBoxValModel m_debtCash = new ComboBoxValModel(Arrays.asList(selectedValue));
        comboDebtCash.setModel(m_debtCash);

        comboDebtCash.setSelectedIndex(0);
    }

    public JComboBox getComboDebtCash() {
        return comboDebtCash;
    }

    public void setComboDebtCash(JComboBox comboDebtCash) {
        this.comboDebtCash = comboDebtCash;
    }

    public JLabel getLblDebtCash() {
        return lblDebtCash;
    }

    public void setLblDebtCash(JLabel lblDebtCash) {
        this.lblDebtCash = lblDebtCash;
    }

    @Override
    public Object createValue() throws BasicException {
        return new Object[]{
                comboDebtCash.getSelectedItem() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, comboDebtCash.getSelectedItem()
        };
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(new Datas[]{Datas.OBJECT, Datas.STRING});
    }

    @Override
    public void init(AppView app) {

    }

    @Override
    public void activate() throws BasicException {

    }

    @Override
    public Component getComponent() {
        return this;
    }

    private void initComponents() {
        lblDebtCash = new javax.swing.JLabel();
        comboDebtCash = new javax.swing.JComboBox();

        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 50));

        lblDebtCash.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDebtCash.setText(AppLocal.getIntString("label.stockreason")); // NOI18N
        lblDebtCash.setPreferredSize(new java.awt.Dimension(110, 30));

        comboDebtCash.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboDebtCash.setMaximumRowCount(12);
        comboDebtCash.setPreferredSize(new java.awt.Dimension(250, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblDebtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboDebtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboDebtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDebtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
    }
}
