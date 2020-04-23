package manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DB.BasketlistDAO;

import DB.GoodsDAO;
import DB.GoodsDTO;


public class ShoppingMall extends JFrame {
	Scanner in = new Scanner(System.in);
	String header[] = { "��ǰ�ڵ�", "��ǰ�̸�", "����", "����", "üũ" };
	JTabbedPane tabpane = new JTabbedPane();
	DefaultTableModel tablemodel = new DefaultTableModel(header, 0);
	JTable table = new JTable(tablemodel);
	JScrollPane tableScroll = new JScrollPane(table);

	// center panel
	JPanel tab_center = new JPanel();
	JPanel tab_2 = new JPanel();
	JPanel tab_south = new JPanel();
	JPanel south_north = new JPanel();

	 JTextField[] txtfield = new JTextField[0];
	JTextField tfield = null;

	int modIntRow = -1;

	String fileName = "data.txt";

	 GoodsDAO dao = GoodsDAO.getInstance();
	 GoodsDTO dto = null;
	 ArrayList<String[]> initList = new ArrayList<>();
	// basketlist list = null;

	public ShoppingMall() {
		super("���θ�");// super�� ������ ȣ��
		Dimension size = new Dimension(600, 400);
		createpanel();
		createtb();
		tablesetting();

		 init();

		this.setLocation(300, 300);
		this.setSize(size);
		this.add(tabpane);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

	}

	 private void init() {
	 initList = dao.getList();
	 for (int i = 0; i < initList.size(); i++) {
	 tablemodel.addRow(initList.get(i));
	 }
	 }

	public void tablesetting() {
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {// ���콺 ���� Ŭ��
					modIntRow = table.getSelectedRow();
					// for (int i = 0; i < txtfield.length; i++) {
					// txtfield[i].setText((String) table.getValueAt(modIntRow,
					// i));
					// }
					// modIntRow = table.getSelectedRow();
					// tfield.setText((String) table.getValueAt(modIntRow, 5));
					// }
					if (e.getClickCount() == 2) {// ���� ���� Ŭ��

					}
					if (e.getClickCount() == 3) {

						modIntRow = table.getSelectedRow();
					}
				}
			}
		});
		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();// ���̺��� ��
																		// ���� ����
		ts.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tc = table.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}

	private void createtb() {
		 south_north.setLayout(new BoxLayout(south_north, BoxLayout.X_AXIS));
		 for (int i = 0; i < txtfield.length; i++) {
		 south_north.add(txtfield[i] = new JTextField());
		 }
		JLabel all = new JLabel("�� �ݾ�");
		south_north.add(all);

		JTextField total = new JTextField(10);
		south_north.add(total);

		JButton addB = new JButton("��ٱ��Ͽ� ���");
		south_north.add(addB);

		addB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[5];
				 for (int i = 0; i < txtfield.length; i++) {
				 in[i] = txtfield[i].getText();
				 txtfield[i].setText("");
				 }
				 int sum = Integer.parseInt(in[3]) * Integer.parseInt(in[4]);
				 in[5] = Integer.toString(sum);
				 dto = new GoodsDTO();
				 dto.setTotal(in[5]);
				tablemodel.addRow(in);
				 saveToDB(in);
			}

		});

		JButton modB = new JButton("�ٷ� �ֹ��ϱ�");
		south_north.add(modB);
		modB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[5];
				 for (int i = 0; i < txtfield.length; i++) {
				 in[i] = txtfield[i].getText();
				 txtfield[i].setText("");
				 }
				 int sum = Integer.parseInt(in[3]) * Integer.parseInt(in[4]);
				 in[5] = Integer.toString(sum);
				 delTableRow(modIntRow);
				 tablemodel.insertRow(modIntRow, in);
				 dto = new GoodsDTO();
				 editToDB(in);
				modIntRow = -1;
			}

			private void delTableRow(int modIntRow) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	private void saveToDB(String[] in) {
		dto = new GoodsDTO();
		int code = Integer.parseInt(in[0]);
		dto.setCode(code);
		dto.setCname(in[1]);
		int cnt = Integer.parseInt(in[2]);
		dto.setCnt(cnt);
		int price = Integer.parseInt(in[3]);
		dto.setPrice(price);
		dao.Insert(dto);

	}
	private void editToDB(String[] in) {
		dto = new GoodsDTO();
		int code = Integer.parseInt(in[0]);
		dto.setCode(code);
		dto.setCname(in[1]);
		int cnt = Integer.parseInt(in[2]);
		dto.setCnt(cnt);
		int price = Integer.parseInt(in[3]);
		dto.setPrice(price);
		dao.Update(dto);
	}

	private void createpanel() {
		this.add(tab_center, "Center");
		this.add(tab_south, "South");
		this.add(tab_2,"N");

		tab_center.setLayout(new BorderLayout());
		tab_center.add(tableScroll, "Center");
		tab_center.add(tab_south, "South");
		tabpane.add("shopping", tab_center);
		
		tab_2.setLayout(new BorderLayout());
		tab_center.add(tableScroll, "Center");
		tab_2.add(tab_2,"South");
		tabpane.add("��ٱ���", tab_2);

		tab_south.setLayout(new BorderLayout());
		tab_south.add(south_north, "North");
		// tab_south.add(south_south, "South");

	}
//	class tab2 extends JPanel {
//		String header[] = { "��ǰ�ڵ�", "��ǰ�̸�", "����", "����", "üũ" };
//		DefaultTableModel tablemodel = new DefaultTableModel(header, 0);
//		JScrollPane tableScroll = new JScrollPane(table);
//		
//		public tab2() {
//			tablesett();
//		}
//		public void tablesett() {
//			table.setRowMargin(0);
//			table.getColumnModel().setColumnMargin(0);
//			table.getTableHeader().setReorderingAllowed(false);
//			table.getTableHeader().setResizingAllowed(false);
//
//			table.setShowVerticalLines(false);
//			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//			table.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					if (e.getButton() == 1) {// ���콺 ���� Ŭ��
//						modIntRow = table.getSelectedRow();
//						// for (int i = 0; i < txtfield.length; i++) {
//						// txtfield[i].setText((String) table.getValueAt(modIntRow,
//						// i));
//						// }
//						// modIntRow = table.getSelectedRow();
//						// tfield.setText((String) table.getValueAt(modIntRow, 5));
//						// }
//						if (e.getClickCount() == 2) {// ���� ���� Ŭ��
//
//						}
//						if (e.getClickCount() == 3) {
//
//							modIntRow = table.getSelectedRow();
//						}
//					}
//				}
//			});
//			DefaultTableCellRenderer ts = new DefaultTableCellRenderer();// ���̺��� ��
//																			// ���� ����
//			ts.setHorizontalAlignment(SwingConstants.CENTER);
//			TableColumnModel tc = table.getColumnModel();
//			for (int i = 0; i < tc.getColumnCount(); i++) {
//				tc.getColumn(i).setCellRenderer(ts);
//			}
//
//		}
//	}

}
