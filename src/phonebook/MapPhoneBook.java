package phonebook;

import java.io.Serializable;
import java.util.*;

/**
 * Created by ha8040we-s on 22/02/17.
 */
public class MapPhoneBook implements PhoneBook, Serializable{

    Map<String, Set<String>> phonebook;
    private static final long serialVersionUID= 1L;

    public MapPhoneBook() {
        phonebook = new TreeMap<>();
    }

    @Override
    public boolean put(String name, String number) {
        Set<String> mightExist = phonebook.get(name);
        if(mightExist != null){
            return mightExist.add(number);
        } else{
            Set<String> temp = new TreeSet<>();
            temp.add(number);
            phonebook.put(name, temp);
            return true;
        }
    }

    @Override
    public boolean remove(String name) {
        Set<String> result = phonebook.remove(name);
        if(result == null)
            return false;
        else return true;
    }

    @Override
    public boolean removeNumber(String name, String number) {
        Set<String> phoneNumbers = phonebook.get(name);
        if(phoneNumbers == null)
            return false;
            else return phoneNumbers.remove(number);
    }

    @Override
    public Set<String> findNumbers(String name) {
        Set<String> result = phonebook.get(name);
        if(result == null)
            return new TreeSet<>();
        else
            return new TreeSet<>(result);
    }

    @Override
    public Set<String> findNames(String number) {
        Set<String> names = new TreeSet<>();

        for(Map.Entry<String, Set<String>> tmp: phonebook.entrySet()){ //kan man använda streams????
            if(tmp.getValue().contains(number))
                names.add(tmp.getKey());
        }
        return names;
    }

    @Override
    public Set<String> names() {
        return new TreeSet<>(phonebook.keySet());
    }

    @Override
    public int size() {
        return phonebook.size();
    }

    @Override
    public boolean isEmpty() {
        return phonebook.isEmpty();
    }
}
