package diaryManagement.controller;

import diaryManagement.domain.DiaryVO;
import diaryManagement.repository.DiaryRepository;
import diaryManagement.view.*;
import centerFrame.CenterFrame;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DiaryController extends JFrame {
    DiaryRepository repo = new DiaryRepository();
    DiarySearchView viewSearch = new DiarySearchView();
    DiaryInsertView viewInsert = new DiaryInsertView();
    DiaryUpdateView viewUpdate = new DiaryUpdateView();
    JTabbedPane tab = new JTabbedPane();
    ArrayList<DiaryVO> list;

    public DiaryController() {
        viewSearch.initView();
        viewInsert.initView();
        viewUpdate.initView();

        refresh();

        viewSearch.getBtnSearch().addActionListener(e -> {
            list = repo.select(viewSearch.getSearchWord(), viewSearch.getCombo().getSelectedIndex());
            viewSearch.setDiaryVOList(list);
            viewSearch.putSearchResult();
        });

        viewSearch.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = viewSearch.getTable().getSelectedRow();
                if(row != -1) {
                    viewSearch.setDetailText(list.get(row).getContent());
                    if (e.getClickCount() == 2) {
                        viewUpdate.setFields(list.get(row));
                        tab.setSelectedIndex(2);
                        JOptionPane.showMessageDialog(null, "수정 모드로 진입합니다.");
                    }
                }
            }
        });

        viewInsert.getBtnAdd().addActionListener(e -> {
            repo.insert(viewInsert.neededInsertData());
            refresh();
            viewInsert.initInsertData();
            JOptionPane.showMessageDialog(null, "작성 완료!");
            tab.setSelectedIndex(0);
        });

        viewUpdate.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = viewUpdate.getTable().getSelectedRow();
                if(row != -1) viewUpdate.setFields(list.get(row));
            }
        });

        viewUpdate.getBtnUpdate().addActionListener(e -> {
            repo.update(viewUpdate.neededUpdateData());
            refresh();
            viewUpdate.initUpdateData(); // 수정 후 입력창 비우기
            JOptionPane.showMessageDialog(null, "수정 완료!");
            tab.setSelectedIndex(0);
        });

        viewUpdate.getBtnDelete().addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    repo.delete(Integer.parseInt(viewUpdate.getNo()));
                    refresh();
                    viewUpdate.initUpdateData(); // 삭제 후 입력창 비우기
                    JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");
                    tab.setSelectedIndex(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "삭제할 일기를 선택해주세요.");
                }
            }
        });

        tab.add("일기 조회", viewSearch);
        tab.add("일기 작성", viewInsert);
        tab.add("수정 및 삭제", viewUpdate);

        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                refresh();
                viewSearch.clearDetail();
                viewUpdate.initUpdateData();
                viewInsert.initInsertData();
            }
        });

        add(tab);
        setTitle("나만의 일기장 (User: 6)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            CenterFrame cf = new CenterFrame(600, 600);
            cf.centerXY();
            setBounds(cf.getX(), cf.getY(), cf.getFw(), cf.getFh());
        } catch(Exception e) { setBounds(100, 100, 600, 600); }
        setVisible(true);
    }

    void refresh() {
        list = repo.select("", 0);
        viewSearch.setDiaryVOList(list);
        viewSearch.putSearchResult();
        viewUpdate.setList(list);
        viewUpdate.putSearchResult();
    }

    public static void main(String[] args) { new DiaryController(); }
}