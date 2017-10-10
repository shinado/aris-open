package indi.shinado.piping.saas;


import indi.shinado.piping.account.User;

/**
 * Created by shinado on 12/09/2017.
 */

public interface IAuth {

    void signInWithEmailAndPassword(final String email, final String password,
                                    final CompleteListener listener);

    void createUser(final User u, final CompleteListener listener);

    void createUserWithEmailAndPassword(final String email, final String password,
                                        final CompleteListener listener);

    interface CompleteListener {
        int FLAG_USER_EXIST = -1;
        int FLAG_USER_NOT_EXIST = 0;
        int FLAG_PWD_WRONG = 1;

        void onComplete(User user);
        void onFail(int flag, String msg);
    }
}
