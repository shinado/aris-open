package indi.shinado.piping.saas.abs;

import indi.shinado.piping.account.User;
import indi.shinado.piping.saas.IAuth;

public class AbsAuth implements IAuth{

    @Override
    public void signInWithEmailAndPassword(String email, String password, CompleteListener listener) {

    }

    @Override
    public void createUser(User u, CompleteListener listener) {

    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, CompleteListener listener) {

    }
}
