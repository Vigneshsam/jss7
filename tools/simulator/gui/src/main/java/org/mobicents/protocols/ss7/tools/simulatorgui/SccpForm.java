/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.tools.simulatorgui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.mobicents.protocols.ss7.tools.simulator.level2.SccpManMBean;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author sergey vetyutnev
 * 
 */
public class SccpForm extends JDialog {

	private SccpManMBean sccp;
	
	private static final long serialVersionUID = 7571177143420596631L;
	private JTextField tbDpc;
	private JTextField tbOpc;
	private JTextField tbNi;
	private JTextField tbRemoteSsn;

	public SccpForm(JFrame owner) {
		super(owner, true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("SCCP settings");
		setBounds(100, 100, 514, 261);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblDpc = new JLabel("Dpc");
		lblDpc.setBounds(10, 14, 46, 14);
		panel.add(lblDpc);
		
		tbDpc = new JTextField();
		tbDpc.setColumns(10);
		tbDpc.setBounds(154, 11, 129, 20);
		panel.add(tbDpc);
		
		tbOpc = new JTextField();
		tbOpc.setColumns(10);
		tbOpc.setBounds(154, 42, 129, 20);
		panel.add(tbOpc);
		
		JLabel lblOpc = new JLabel("Opc");
		lblOpc.setBounds(10, 45, 46, 14);
		panel.add(lblOpc);
		
		tbNi = new JTextField();
		tbNi.setColumns(10);
		tbNi.setBounds(154, 73, 129, 20);
		panel.add(tbNi);
		
		tbRemoteSsn = new JTextField();
		tbRemoteSsn.setColumns(10);
		tbRemoteSsn.setBounds(154, 104, 129, 20);
		panel.add(tbRemoteSsn);
		
		JLabel lblNetworkIndicatpr = new JLabel("Network indicator");
		lblNetworkIndicatpr.setBounds(10, 76, 112, 14);
		panel.add(lblNetworkIndicatpr);
		
		JLabel lblRemoteSsn = new JLabel("Remote ssn");
		lblRemoteSsn.setBounds(10, 107, 112, 14);
		panel.add(lblRemoteSsn);
		
		JButton button = new JButton("Load default values for side A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataA();
			}
		});
		button.setBounds(10, 145, 245, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("Load default values for side B");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataB();
			}
		});
		button_1.setBounds(265, 145, 234, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Reload");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadData();
			}
		});
		button_2.setBounds(10, 179, 144, 23);
		panel.add(button_2);
		
		JButton button_3 = new JButton("Save");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saveData()) {
					getJFrame().dispose();
				}
			}
		});
		button_3.setBounds(255, 179, 117, 23);
		panel.add(button_3);
		
		JButton button_4 = new JButton("Cancel");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getJFrame().dispose();
			}
		});
		button_4.setBounds(382, 179, 117, 23);
		panel.add(button_4);
	}

	public void setData(SccpManMBean sccp) {
		this.sccp = sccp;
		
		this.reloadData();
	}

	private JDialog getJFrame() {
		return this;
	}

	private void reloadData() {
		tbDpc.setText(((Integer) this.sccp.getDpc()).toString());
		tbOpc.setText(((Integer) this.sccp.getOpc()).toString());
		tbNi.setText(((Integer) this.sccp.getNi()).toString());
		tbRemoteSsn.setText(((Integer) this.sccp.getRemoteSsn()).toString());
	}

	private void loadDataA() {
		tbDpc.setText("2");
		tbOpc.setText("1");
		tbNi.setText("2");
		tbRemoteSsn.setText("8");
	}

	private void loadDataB() {
		tbDpc.setText("1");
		tbOpc.setText("2");
		tbNi.setText("2");
		tbRemoteSsn.setText("8");
	}

	private boolean saveData() {
		int dpc = 0;
		int opc = 0;
		int ni = 0;
		int remoteSsn = 0;
		try {
			dpc = Integer.parseInt(tbDpc.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing Dpc value: " + e.toString());
			return false;
		}
		try {
			opc = Integer.parseInt(tbOpc.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing Opc value: " + e.toString());
			return false;
		}
		try {
			ni = Integer.parseInt(tbNi.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing Network indicator value: " + e.toString());
			return false;
		}
		try {
			remoteSsn = Integer.parseInt(tbRemoteSsn.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing RemoteSsn value: " + e.toString());
			return false;
		}

		this.sccp.setDpc(dpc);
		this.sccp.setOpc(opc);
		this.sccp.setNi(ni);
		this.sccp.setRemoteSsn(remoteSsn);
		return true;
	}
}