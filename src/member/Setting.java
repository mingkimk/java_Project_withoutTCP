package member;




import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

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

import manager.GoodsDAO;
import manager.GoodsDTO;

public class Setting extends JFrame {

	Scanner in = new Scanner(System.in);
	String header[] = { "이름", "아이디", "상품", "수량", "단가", "총합" };
	JTabbedPane tabpane = new JTabbedPane();
	DefaultTableModel tablemodel = new DefaultTableModel(header, 0);
	JTable table = new JTable(tablemodel);
	JScrollPane tableScroll = new JScrollPane(table);

	// center panel
	JPanel tab_center = new JPanel();
	JPanel tab_south = new JPanel();
	JPanel south_north = new JPanel();
	JPanel south_south = new JPanel();

	JTextField[] txtfield = new JTextField[5];
	JTextField tfield = null;

	int modIntRow = -1;

	String fileName = "data.txt";

	GoodsDAO dao = GoodsDAO.getInstance();
	GoodsDTO dto = null;
	ArrayList<String[]> initList = new ArrayList<>();
	basketlist list = null;

	Setting() {
		super("관리자용 창");// super의 생성자 호출
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
				if (e.getButton() == 1) {// 마우스 왼쪽 클릭
					modIntRow = table.getSelectedRow();
					for (int i = 0; i < txtfield.length; i++) {
						txtfield[i].setText((String) table.getValueAt(modIntRow, i));
					}
					modIntRow = table.getSelectedRow();
					tfield.setText((String) table.getValueAt(modIntRow, 5));
				}
				if (e.getClickCount() == 2) {// 왼쪽 더블 클릭

				}
				if (e.getClickCount() == 3) {

					modIntRow = table.getSelectedRow();

				}
			}
		});
		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();// 테이블의 셀 값을 정렬
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
		JButton addB = new JButton("추가");
		south_north.add(addB);

		addB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[6];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				int sum = Integer.parseInt(in[3]) * Integer.parseInt(in[4]);
				in[5] = Integer.toString(sum);
				tablemodel.addRow(in);
				saveToDB(in);
			}

		});

		JButton modB = new JButton("수정");
		south_north.add(modB);
		modB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[6];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				int sum = Integer.parseInt(in[3]) * Integer.parseInt(in[4]);
				in[5] = Integer.toString(sum);
				delTableRow(modIntRow);
				tablemodel.insertRow(modIntRow, in);
				dto = new DTO();
				editToDB(in);
				modIntRow = -1;
			}
		});

		JButton delB = new JButton("삭제");
		south_north.add(delB);
		delB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[6];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				delToDB(in);
				delTableRow(table.getSelectedRow());
			}
		});
		south_south.add(tfield = new JTextField(15));
		JButton orderB = new JButton("주문");
		south_south.add(orderB);

		orderB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[6];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}

				list = new basketlist();
				list.initList(in);
			}
		});

		JButton orderallB = new JButton("전체주문");
		south_south.add(orderallB);

		orderallB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[6];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}

				list = new basketlist();
				list.init(in);
			}
		});

	}

	private void saveToDB(String[] in) {
		dto = new DTO();
		dto.setName(in[0]);
		dto.setId(in[1]);
		dto.setGoods(in[2]);
		dto.setCnt(in[3]);
		dto.setPrice(in[4]);
		dto.setTotal(in[5]);
		dao.Insert(dto);

	}

	private void editToDB(String[] in) {
		dto = new DTO();
		dto.setName(in[0]);
		dto.setId(in[1]);
		dto.setGoods(in[2]);
		dto.setCnt(in[3]);
		dto.setPrice(in[4]);
		dto.setTotal(in[5]);
		dao.editinfo(dto);
	}

	private void delToDB(String[] in) {
		dto = new DTO();
		dto.setName(in[0]);
		dto.setId(in[1]);
		dto.setGoods(in[2]);
		dto.setCnt(in[3]);
		dto.setPrice(in[4]);
		dto.setTotal(in[5]);
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