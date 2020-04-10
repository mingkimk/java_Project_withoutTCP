package member;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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



public class ShoppingMall extends JFrame {
	String header[] = { "코드", "상품이름","수량", "단가"};
	String contents[][] = {};

	JTabbedPane tabPane = new JTabbedPane();

	DefaultTableModel tableModel = new DefaultTableModel(contents, header);
	// DefaultTableModel 모델과 데이터를 연결해줌
	// 데이터를 복사해서 추가한 것이 아닌 링크해서 추가한 것입니다.
	// 모델을 안쓰게되면 새로만들어야한다.

	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);
//데이터를 바꾸고 싶다면 요래하기때문에 테이블을 안건들인다
	// 테이블에 직접 접근 하지 않고 가능

	
	JPanel tab_1 = new JPanel();
	JPanel tab_1_inputP = new JPanel();
	JPanel tab_2 = new JPanel();

	JTextField[] indata = new JTextField[1];
	
//	JLabel TotalLabel;
//	JTextField TotalField;
	JPopupMenu popup;
	JMenuItem m_del, m_mod, m_add;

	int modIntRow = -1;
	int addIntRow = -1;
	
	int sumRow=0;

	String fileName = "data.txt";

	GoodsDAO sqlDAO = GoodsDAO.getInstance();
	List<String[]> initList = new ArrayList<>();
	ArrayList<GoodsDTO> dList = new ArrayList<>();
	public ShoppingMall() {
		super("SSG");
		Dimension size = new Dimension(600, 400);
		menuLayout();// 팝업메뉴
		tableSetting();
		createInputP();
		createTabbedP();

		init(); // frame이 실행되고 모든 컴포넌트가 생성되면 초기 데이터 값을 가져오는 메서드

		this.setLocation(300, 300);
		this.setSize(size);
		this.add(tabPane);
		// this.setPreferredSize(size);

		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

	public void init() {
		initList = sqlDAO.getList();
		for (int i = 0; i < initList.size(); i++) {
			tableModel.addRow(initList.get(i));
		}
	}

	public void menuLayout() {
		popup = new JPopupMenu();
		m_mod = new JMenuItem("수정");
		m_mod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// 수정하고 싶은 row를 반환하여서 해당되는 정보를 textfield에 보여지게 하겠습니다.
				modIntRow = table.getSelectedRow();
				for (int i = 0; i < indata.length; i++) {
					indata[i].setText((String) table.getValueAt(modIntRow, i));
					
				}
			}
		});
		m_del = new JMenuItem("삭제");
		m_del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() == -1) {
					return;
				} else {
					delTableRow(table.getSelectedRow());
				}
			}
		});
		m_add = new JMenuItem("추가");
		m_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addIntRow = table.getSelectedRow();
				for (int i = 0; i < indata.length; i++) {
					indata[i].setText((String) table.getValueAt(addIntRow, i));
				}
			}
		});
		
		popup.add(m_mod);
		popup.add(m_del);
		popup.add(m_add);
		
	}

	public void delTableRow(int row) {
		tableModel.removeRow(row);
	}

	public void tableSetting() {
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.add(popup);
		table.addMouseListener(new MouseAdapter() { // 마우스 동작 감지 (클릭, 드래그)
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {// 왼쪽 클릭
					int column = table.columnAtPoint(e.getPoint());
					int row = table.rowAtPoint(e.getPoint());
					table.changeSelection(row, column, false, false);
					modIntRow = table.getSelectedRow();
					for (int i = 0; i < indata.length; i++) {
						indata[i].setText((String) table.getValueAt(modIntRow, i));
						
				}
				}
				if (e.getClickCount() == 2) { // 더블 클릭
				}
				if (e.getButton() == 3) {// 오른쪽 클릭
					int column = table.columnAtPoint(e.getPoint());
					int row = table.rowAtPoint(e.getPoint());
					table.changeSelection(row, column, false, false);
					popup.show(table, e.getX(), e.getY());
				}
			}
		});

		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();
		ts.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tc = table.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}
		}
		
		
		
		

	public void createInputP() {
		tab_1_inputP.setLayout(new BoxLayout(tab_1_inputP, BoxLayout.X_AXIS));
		for (int i = 0; i < indata.length; i++) {
			tab_1_inputP.add(indata[i] = new JTextField());
		}
//		JButton addB = new JButton("Add");
//		tab_1_inputP.add(addB);
//		addB.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				String in[] = new String[6];
//				for (int i = 0; i < indata.length; i++) {
//					in[i] = indata[i].getText();
//					indata[i].setText("");
//				}
//				int r=0;
//				String sum="";
//				for (int i = 0; i < indata.length; i++) {
//				 r=Integer.parseInt(indata[3].getText())*Integer.parseInt(indata[4].getText());
//				 sum=Integer.valueOf(r);
//				 result.setText(Integer.toString(r));
//					in[i] = indata[5].getText();
//					indata[i].setText("");
//		           sum=indata[5].getText();
	
				
//				}
//				tableModel.addRow(in);
//				saveToDB(in);
//			}
//		});

//		JButton modB = new JButton("Mod");
//		tab_1_inputP.add(modB);
//		modB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if (modIntRow != -1) {
//					String in[] = new String[6];
//					for (int i = 0; i < indata.length; i++) {
//						in[i] = indata[i].getText();
//						delTableRow(modIntRow);
//						tableModel.insertRow(modIntRow, in);
//					}
//					modIntRow = -1;
//				}
//			}
//		});
//		JButton addB = new JButton("add");
//		tab_1_inputP.add(addB);
//		addB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if (addIntRow != -1) {
//					String in[] = new String[5];
//					for (int i = 0; i < indata.length; i++) {
//						in[i] = indata[i].getText();
//						delTableRow(addIntRow);
//						tableModel.insertRow(addIntRow, in);
//					}
//					addIntRow = -1;
//				}
//			}
//		});
//
//		JButton delB = new JButton("Del");
//		tab_1_inputP.add(delB);
//		delB.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				if (table.getSelectedRow() == -1) {
//					return;
//				} else {
//					delTableRow(table.getSelectedRow());
//				}
//			}
//		});
//		
//		
//		JButton saveB = new JButton("Save");
//		tab_1_inputP.add(saveB);
//		saveB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				// saveToDB();
//			}
//		});

		// 총금액 Label
		// 텍스트 필드 빈칸에 총금액 나오게
		
		
		JLabel TotalLabel = new JLabel("총금액");
		 JTextField TotalF = new JTextField(1);
		tab_1_inputP.add(TotalLabel);
		tab_1_inputP.add(TotalF);
		TotalF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String in[] = new String[6];
				for (int i = 0; i < indata.length; i++) {
					in[i] = indata[i].getText();
					indata[i].setText("");
				}
				tableModel.addRow(in);
				saveToDB(in);
			}
			
		
			
		});
	

	JButton orderB = new JButton("주문");
	tab_1_inputP.add(orderB);
	orderB.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			// saveToDB();
		}
	});
	}

	

	public void saveToDB(String[] in) {
		GoodsDTO newDTO = new GoodsDTO();
		newDTO.setCode(in[0]);
		newDTO.setCname(in[1]);
		newDTO.setCnt(in[2]);
		newDTO.setPrice(in[3]);
	
		sqlDAO.InsertGoods(newDTO);
	}

	public void createTabbedP() {
		tab_1.setLayout(new BorderLayout());
		tab_1.add(tableScroll, "Center");
		tab_1.add(tab_1_inputP, "South");
		tabPane.add("장바구니", tab_1);

//		tab_2.setLayout(new BorderLayout());
//		tab_2.add(new Tab_2_Canvas(), "Ce" + "nter");
//		tabPane.add("", tab_2);
	}
	
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ShoppingMall();
		GoodsDAO DAOobj = new GoodsDAO();
		GoodsDTO c = new GoodsDTO();
	}
}