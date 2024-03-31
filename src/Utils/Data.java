/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import static java.awt.Color.pink;
import static java.awt.Color.white;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author ADMIN
 */
public class Data {
        public Boolean isEmail(String email){
            String pattern = "\\w+@+\\w+(\\.+\\w+){1,3}";
            return email.matches(pattern);
        }
        
        public Boolean isNumber(String number){
             String regexNumber = "[0-9]+";
             return number.matches(regexNumber);
        }
        

        public Boolean isCmnd(String cmnd){
            String pattern = "\\d{12}";
            return cmnd.matches(pattern);
        }

        public Boolean isSdt(String sdt){
            String  pattern = "0\\d{9,10}";
            return sdt.matches(pattern);
        }

        public Boolean isMSSV(String mssv){
            String  pattern = "[A-Z0-9]{7}";
            return mssv.matches(pattern);
        }

        public Boolean isPass(String pass){
            String  pattern = ".{6,}";
            return pass.matches(pattern);
        }

       public Boolean isName(String name){
    String pattern = "[a-zA-ZàáạãâầấậẫăằắặẵẳèéẹẽêềếệễểđìíịĩỉòóọõôồốộỗổơờớợỡởùúụũưừứựữửỳýỵỹỷĂÂĐÊÔƠƯỜỨỰỮỬÀÁẠÃÂẦẤẬẪẨĂẰẮẶẴẲÈÉẸẼÊỀẾỆỄỂÌÍỊĨỈÒÓỌÕÔỒỐỘỖỔƠỜỚỢỠỞÙÚỤŨỪỨỰỮỬỲÝỴỸỶ ]+";
    return name.matches(pattern);
}

        public Boolean isXe(String xe){
            String  pattern = "5\\d+-[A-Z]+\\d+-((\\d{4})|(\\d{3}+\\.+\\d{2}))";
            return xe.matches(pattern);
        }

        public Boolean isWeb(String web){
            String  pattern = "http://www\\.+\\w+(\\.+\\w+){1,3}";
            return web.matches(pattern);
        }
        
        public boolean ContainNumberOnly(String number){
            boolean result = false;
            Pattern pattern = Pattern.compile("[0-9]+");
            pattern = Pattern.compile("\\d+");
            result = pattern.matcher(number).matches();
            return result;
        }
        
        public boolean checkMaCD(String maCD) { 
            
            String pattern = "[a-zA-Z0-9]{5}"; 
            return maCD.matches(pattern);
        }
        
        public  boolean checkMaNH(String txt) { 
            String pattern = "[a-zA-Z0-9]{7}";
            return txt.matches(pattern);
        }
        
        public  boolean isValidDate(String inDate) {

if (inDate == null) { return false;
}

//set the format to use as a constructor argument
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

if (inDate.trim().length() != dateFormat.toPattern().length()) { return false;
}
dateFormat.setLenient(false); try {
//parse the inDate parameter dateFormat.parse(inDate.trim());
} catch (Exception pe) { return false;
}
return true;
}

//định d¿ng dd/MM/yyyy	(ko dc)(hoặc d/M/yyyy nếu là số 0 đÿng trưác)
public  boolean checkDate(String txt) { 

//	if (id.matches(rgx)) {
//	return true;
//	} else {
//	txt.setBackground(pink);
//	dialogHelper.alert(txt.getRootPane(), txt.getName() + " không đúng định d¿ng Date.");
//	return false;
//	}



 
if (isValidDate(txt)) { return true;
} else {

return false;
}
}




        

}
    
