package manager;

import java.awt.BorderLayout;
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

import DB.GoodsDAO;
import DB.GoodsDTO;


public class Setting extends JFrame {

	Scanner in = new Scanner(System.in);
	String header[] = { "��ǰ�ڵ�", "��ǰ�̸�", "����", "����" };
	JTabbedPane tabpane = new JTabbedPane();
	DefaultTableModel tablemodel = new DefaultTableModel(header, 0);
	JTable table = new JTable(tablemodel);
	JScrollPane tableScroll = new JScrollPane(table);

	// center panel
	JPanel tab_center = new JPanel();
	JPanel tab_south = new JPanel();
	JPanel south_north = new JPanel();
	JPanel south_south = new JPanel();

	JTextField[] txtfield = new JTextField[4];
	JTextField tfield = null;

	int modIntRow = -1;

	String fileName = "data.txt";

	GoodsDAO dao = GoodsDAO.getInstance();
	GoodsDTO dto = null;
	ArrayList<String[]> initList = null;

	public Setting() {
		super("������ ����");// super�� ������ ȣ��
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
					for (int i = 0; i < txtfield.length; i++) {
						txtfield[i].setText((String) table.getValueAt(modIntRow, i));
					}
					modIntRow = table.getSelectedRow();
					tfield.setText((String) table.getValueAt(modIntRow, 5));
				}
				if (e.getClickCount() == 2) {// ���� ���� Ŭ��

				}
				if (e.getClickCount() == 3) {

					modIntRow = table.getSelectedRow();

				}
			}
		});
		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();// ���̺��� �� ���� ����
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
		JButton addB = new JButton("�߰�");
		south_north.add(addB);

		addB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				tablemodel.addRow(in);
				saveToDB(in);
			}

		});

		JButton modB = new JButton("����");
		south_north.add(modB);
		modB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				delTableRow(modIntRow);
				tablemodel.insertRow(modIntRow, in);
				editToDB(in);
				modIntRow = -1;
			}
		});

		JButton delB = new JButton("����");
		south_north.add(delB);
		delB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				delToDB(in);
				delTableRow(table.getSelectedRow());
			}
		});

		JButton orderallB = new JButton("���θ�����");
		south_south.add(orderallB);

		orderallB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
					new ShoppingMall();
				}

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

	private void delToDB(String[] in) {
		dto = new GoodsDTO();
		int code = Integer.parseInt(in[0]);
		dto.setCode(code);
		dto.setCname(in[1]);
		int cnt = Integer.parseInt(in[2]);
		dto.setCnt(cnt);
		int price = Integer.parseInt(in[3]);
		dto.setPrice(price);
		dao.delinfo(dto);
	}

	private void delTableRow(int row) {
		tablemodel.removeRow(row);

	}

	private void createpanel() {
		this.add(tab_center, "Center");
		this.add(tab_south, "South");

		tab_center.setLayout(new BorderLayout());
		tab_center.add(tableScroll, "Center");
		tab_center.add(tab_south, "South");
		tabpane.add("basket", tab_center);

		tab_south.setLayout(new BorderLayout());
		tab_south.add(south_north, "North");
		tab_south.add(south_south, "South");

	}

}









