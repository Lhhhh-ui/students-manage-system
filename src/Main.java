import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 用户实体类
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 验证密码
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // 获取用户名
    public String getUsername() {
        return username;
    }
}



class Student{
    private String id;
    private String name;
    private String sex;
    private String age;

    public Student( String id,String name, String sex,String age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age=age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() { return sex; }

    public String getage(){ return age; }

    @Override
    public String toString() {
        return "学号："+id+"学生姓名："+name+"性别："+sex+"年龄："+age;
    }
}



public class Main {

    static final HashMap<String, User> users = new HashMap<>();
    static final List<Student> student = new ArrayList<>();
    static DefaultTableModel tableModel;
    static String[] columnNames = {"学号", "姓名", "性别", "年龄"};

    // 新增：初始化表格模型
    public static void initTableModel() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // 新增：刷新表格数据
    public static void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : student) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getSex(), s.getage()});
        }
    }



    //登录注册功能
    public static void LoginPage(){
        JFrame f = new JFrame("登录注册系统");
        f.setSize(400, 260);
        f.setLocation(300, 200);
        f.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("用户登录 / 注册", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel userLabel = new JLabel("用户名：");
        JLabel passLabel = new JLabel("密码：");

        final JTextField userText = new JTextField();
        final JPasswordField passText = new JPasswordField();

        centerPanel.add(userLabel);
        centerPanel.add(userText);
        centerPanel.add(passLabel);
        centerPanel.add(passText);

//登陆注册清空按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton loginBtn = new JButton("登录");
        JButton registerBtn = new JButton("注册");
        JButton clearBtn = new JButton("清空");

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(clearBtn);

        f.add(titleLabel, BorderLayout.NORTH);
        f.add(centerPanel, BorderLayout.CENTER);
        f.add(buttonPanel, BorderLayout.SOUTH);

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);

        // 注册限制
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passText.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "用户名和密码不能为空！");
                } else if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(f, "用户名已存在，注册失败！");
                } else {
                    User user = new User(username, password);
                    users.put(username, user);
                    JOptionPane.showMessageDialog(f, "注册成功！");
                    userText.setText("");
                    passText.setText("");
                }
            }
        });

        // 登录限制
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passText.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "用户名和密码不能为空！");
                } else if (!users.containsKey(username)) {
                    JOptionPane.showMessageDialog(f, "用户名不存在！");
                } else {
                    User user = users.get(username);
                    if (user.checkPassword(password)) {
                        JOptionPane.showMessageDialog(f, "登录成功，欢迎你：" + user.getUsername());
                        //关闭当前窗口
                        f.dispose();
//                        开启一个主界面窗口
                        showStudentWindows(user);
                    } else {
                        JOptionPane.showMessageDialog(f, "密码错误，登录失败！");
                    }
                }
            }
        });

        // 清空按钮
        clearBtn.addActionListener(e -> {
            userText.setText("");
            passText.setText("");
        });
    }
    public static void main(String[] args) {
        initTableModel();  // 新增：初始化表格模型
        LoginPage();

    }
    public static  void showStudentWindows(User user){
//        定义窗口
        JFrame jFrame=new JFrame("学生信息管理系统");
        jFrame.setSize(500, 500);
        jFrame.setLocation(300,200);
        jFrame.setLayout(new BorderLayout());
//        定义border布局里面每格的内容
        JLabel titleLabel = new JLabel("欢迎登录学生信息管理系统，用户名："+user.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        jFrame.add(titleLabel, BorderLayout.NORTH);
//定义按钮
        JPanel buttonPanel = new JPanel(new GridLayout(7,1,10,15));
        JButton addButton=new JButton("添加学生信息");
        JButton delButton=new JButton("删除学生信息");
        JButton updateButton=new JButton("编辑学生信息");
        JButton searButton=new JButton("查看学生信息");
        JButton clearButton=new JButton("清空输入");
        JButton fButton=new JButton("浏览全部学生");
        JButton outButton=new JButton("退出登录");

        buttonPanel.add(addButton);
        buttonPanel.add(delButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(searButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(fButton);
        buttonPanel.add(outButton);

        jFrame.add(buttonPanel, BorderLayout.EAST);

//        学生信息部分
        JPanel inputPanel=new JPanel(new GridLayout(5,1,5,5));
//        可以给面板设置边框
        inputPanel.setBorder(BorderFactory.createTitledBorder("学生信息"));

        JLabel idLabel=new JLabel("学号:");
        JLabel nameLabel=new JLabel("学生姓名:");
        JLabel sexLabel=new JLabel("性别:");
        String[] sex={"男","女"};
        JLabel ageLabel=new JLabel("年龄:");

        JTextField idText=new JTextField(10);
        JTextField nameText=new JTextField(10);
        JComboBox<String> sexComboBox=new JComboBox<>(sex);
        sexComboBox.setPreferredSize(new Dimension(100,25));
        JTextField ageText=new JTextField(10);




        // 学号行
        JPanel idRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        idRow.add(idLabel);
        idRow.add(idText);
        inputPanel.add(idRow);

// 姓名行
        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        nameRow.add(nameLabel);
        nameRow.add(nameText);
        inputPanel.add(nameRow);

// 性别行
        JPanel genderRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        genderRow.add(sexLabel);
        genderRow.add(sexComboBox);
        inputPanel.add(genderRow);


//年龄行
        JPanel ageRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        ageRow.add(ageLabel);
        ageRow.add(ageText);
        inputPanel.add(ageRow);


        jFrame.add(inputPanel, BorderLayout.CENTER);

//跳转浏览页面

        fButton.addActionListener(e->{
            showAllStudentWindow(user);
        });




//        学生信息的展示

        JTextArea studentArea=new JTextArea();
        studentArea.setEditable(false);
        studentArea.setFont(new Font("微软雅黑",Font.BOLD,20));

        JScrollPane scrollPane = new JScrollPane(studentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("学生列表"));
        scrollPane.setSize(600, 200);
        jFrame.add(scrollPane, BorderLayout.SOUTH);

        jFrame.setVisible(true);



//        增加学生信息的点击事件
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText().trim();
                String name = nameText.getText().trim();
                String sex = (String) sexComboBox.getSelectedItem();
                String age = ageText.getText().trim();
                if (id.isEmpty() || name.isEmpty() || sex.isEmpty() || age.isEmpty()) {
                    JOptionPane.showMessageDialog(jFrame,"学生信息不能为空!");
                    return;
                }
                for(int i=0;i<student.size();i++)
                {
                    Student students=student.get(i);
                    if(students.getId().equals(id)){
                        JOptionPane.showMessageDialog(jFrame,"该学生信息已存在！");
                        return;
                    }
                }
                Student newStudent = new Student(id, name, sex,age);
                student.add(newStudent);
                refreshTable();  // 新增：刷新表格
                JOptionPane.showMessageDialog(jFrame,"新增学生信息成功！");


                showALLStudents(studentArea);
                idText.setText("");
                nameText.setText("");
                sexComboBox.setSelectedIndex(0);
                ageText.setText("");
            }
        });

        //删除点击事件
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText().trim();
                for(int i=0;i<student.size();i++){
                    Student students=student.get(i);
                    if(students.getId().equals(id)){
                        student.remove(i);
                        refreshTable();  // 新增：刷新表格
                        JOptionPane.showMessageDialog(jFrame,"删除成功！");

                        showALLStudents(studentArea);
                        idText.setText("");
                        nameText.setText("");
                        sexComboBox.setSelectedIndex(0);
                        ageText.setText("");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(jFrame,"删除失败，没有找到该学生信息！");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String id =idText.getText().trim();
                String name=nameText.getText().trim();
                String sex=(String)sexComboBox.getSelectedItem();
                String age=ageText.getText().trim();
                if (id.isEmpty() || name.isEmpty() || sex.isEmpty() || age.isEmpty()) {
                    JOptionPane.showMessageDialog(jFrame,"学生信息不能为空!");
                    return;
                }
                for(int i=0;i<student.size();i++)
                {
                    Student s = student.get(i);
                    if (s.getId().equals(id)) {
                        student.set(i, new Student(id, name, sex, age));
                        refreshTable();  // 新增：刷新表格
                        JOptionPane.showMessageDialog(jFrame, "修改成功！");
                        showALLStudents(studentArea);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(jFrame,"未找到该学生，无法修改！");
            }
        });



        searButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idText.getText().trim();
                for(int i=0;i<student.size();i++){
                    Student students=student.get(i);
                    if(students.getId().equals(id)){

                        JOptionPane.showMessageDialog(jFrame,"该学生信息存在："+students.toString());
                        return;

                    }

                }
                JOptionPane.showMessageDialog(jFrame,"没有找到该学生信息！");
            }
        });



        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idText.setText("");
                nameText.setText("");
                sexComboBox.setSelectedIndex(0);
                ageText.setText("");
            }
        });

        outButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                LoginPage();
            }
        });



        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }




    private static void showALLStudents(JTextArea studentArea) {
        if(student.size()==0)
        {
            studentArea.setText("暂无学生信息");
            return;
        }
        String text="";
        for(int i=0;i<student.size();i++)
        {
            text +=student.get(i).toString()+"\n";
        }
        studentArea.setText(text);
    }




    public static void showAllStudentWindow(User user){
        JFrame FFrame =new JFrame("学生信息展示页面");
        FFrame.setSize(450, 400);
        FFrame.setLocation(300,200);
        FFrame.setLayout(new BorderLayout());
//        定义border布局里面每格的内容
        JLabel titleLabel = new JLabel("全体学生信息列表，用户名："+user.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        FFrame.add(titleLabel, BorderLayout.NORTH);

        // 使用共享的表格模型创建表格
        JTable table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));


        JScrollPane scroll = new JScrollPane(table);
        FFrame.add(scroll, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> refreshTable());


        JButton closeBtn = new JButton("关闭本页面");
        closeBtn.addActionListener(e -> FFrame.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshBtn);
        bottomPanel.add(closeBtn);
        FFrame.add(bottomPanel, BorderLayout.SOUTH);

        FFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FFrame.setVisible(true);


    }

}