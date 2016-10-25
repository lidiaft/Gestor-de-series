package abd.pr1.swing;

import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ActuaTable extends AbstractTableModel {

	private String[] columnNames = { "Serie", "Episodio", "Personaje"};
	protected TreeMap<Integer, String[]> content; 
	//Object[][] rowData;
	
	public ActuaTable(){
		content = new TreeMap<Integer, String[]>();		
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return content.keySet().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String[] datos = (String[])content.values().toArray()[row];
		return datos[col];
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public void setValue(String[] value) {
		content.put(getRowCount(), value );
	}
	
	public String getTituloEpisodio(int row) {
		return (String) getValueAt(row,2 );
	}
}
