package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import stock.CSVFormatException;
import delivery.DeliveryException;
import stock.InventoryManager;
import stock.Store;


public class Client extends JFrame {
	Store store;
	private JLabel jlb_capital = new JLabel("100,000.00");
	private JTable table = new JTable();
	StockTableModel model;
	public Client() throws CSVFormatException {
		super();
		store = Store.getStore();
		model = new StockTableModel(store.getStock());
		buildGui();
	}
	
	private void buildGui() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(new JLabel("Capital"), BorderLayout.WEST);
		jp.add(jlb_capital , BorderLayout.EAST);
		
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(new FlowLayout());
		
		JButton jlb_loadManifest = new JButton("Load Manifest");
		JButton jlb_loadProperties = new JButton("Load Properties");
		JButton jlb_exportManifest = new JButton("Export Manifest");
		JButton jlb_loadSales = new JButton("Load Sales");
		
		jpSouth.add(jlb_loadProperties);
		jpSouth.add(jlb_loadManifest);
		jpSouth.add(jlb_loadSales);
		jpSouth.add(jlb_exportManifest);
		
		jlb_exportManifest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(jp);
				File f = chooser.getSelectedFile();
				try {
					store.exportManifest(f);
				} catch (IOException | DeliveryException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jp, "Could not export manifest", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		jlb_loadManifest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(jp);
				File f = chooser.getSelectedFile();
				try {
					store.loadManifest(f);
				} catch (IOException | DeliveryException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jp, "Could not load manifest", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		


		jlb_loadProperties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(jp);
				File f = chooser.getSelectedFile();
				try {
					InventoryManager.readItemProperties(f.getAbsolutePath());
					store.initialize();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jp, "Could not load properties", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JScrollPane pane = new JScrollPane(table);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jp, BorderLayout.NORTH);
		getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		
	}
}
