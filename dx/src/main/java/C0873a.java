public class C0873a {

    public static final Character f5208A = Character.valueOf('و');
    public static final Character f5209B = Character.valueOf('ي');
    public static final Character f5210C = Character.valueOf('آ');
    public static final Character f5211D = Character.valueOf('ة');
    public static final Character f5212E = Character.valueOf('ى');
    public static final Character f5213F = Character.valueOf('ک');
    public static final Character f5214G = Character.valueOf('ڪ');
    public static final Character f5215H = Character.valueOf('ی');
    public static final Character f5216I = Character.valueOf('ہ');
    public static final Character f5217J = Character.valueOf('ؤ');
    public static final Character f5218K = Character.valueOf('ء');
    public static final Character f5219L = Character.valueOf('ۃ');
    public static final Character f5220M = Character.valueOf('ؠ');
    public static final Character f5221N = Character.valueOf('أ');
    public static final Character f5222O = Character.valueOf('إ');
    public static final Character f5223P = Character.valueOf('ئ');
    public static final Character f5224Q = Character.valueOf('ػ');
    public static final Character f5225R = Character.valueOf('ؿ');
    public static final Character f5226S = Character.valueOf('ٮ');
    public static final Character f5227T = Character.valueOf('ڨ');
    public static final Character f5228U = Character.valueOf('ګ');
    public static final Character f5229V = Character.valueOf('ۂ');
    public static final Character f5230W = Character.valueOf('ۄ');
    public static final Character f5231X = Character.valueOf('ۋ');
    public static final Character f5232Y = Character.valueOf('ۋ');
    public static final Character f5233Z = Character.valueOf('ۑ');
    public static final Character f5234a = Character.valueOf('ا');
    public static final Character aA = Character.valueOf('ّ');
    public static final Character aB = Character.valueOf('ٰ');
    public static final Character aC = Character.valueOf('ٓ');
    public static final Character aD = Character.valueOf('ـ');
    public static final Character aE = Character.valueOf('ۖ');
    public static final Character aF = Character.valueOf('ۗ');
    public static final Character aG = Character.valueOf('ۘ');
    public static final Character aH = Character.valueOf('ۙ');
    public static final Character aI = Character.valueOf('ۚ');
    public static final Character aJ = Character.valueOf('ۛ');
    public static final Character aa = Character.valueOf('ۮ');
    public static final Character ab = Character.valueOf('ۼ');
    public static final Character ac = Character.valueOf('ۿ');
    public static final Character ad = Character.valueOf('ّ');
    public static final Character ae = Character.valueOf('ْ');
    public static final Character af = Character.valueOf('ۡ');
    public static final Character ag = Character.valueOf('ً');
    public static final Character ah = Character.valueOf('ٌ');
    public static final Character ai = Character.valueOf('ٍ');
    public static final Character aj = Character.valueOf(' ');
    public static final Character ak = Character.valueOf('َ');
    public static final Character al = Character.valueOf('ُ');
    public static final Character am = Character.valueOf('ِ');
    public static final Character an = Character.valueOf('ۢ');
    public static final Character ao = Character.valueOf('ۭ');
    public static final Character ap = Character.valueOf('ٰ');
    public static final Character aq = Character.valueOf('ٓ');
    public static final Character ar = Character.valueOf('ۤ');
    public static final Character as = Character.valueOf('ٗ');
    public static final Character at = Character.valueOf('ً');
    public static final Character au = Character.valueOf('ٌ');
    public static final Character av = Character.valueOf('ٍ');
    public static final Character aw = Character.valueOf('َ');
    public static final Character ax = Character.valueOf('ُ');
    public static final Character ay = Character.valueOf('ِ');
    public static final Character az = Character.valueOf('ؐ');
    public static final Character f5235b = Character.valueOf('ب');
    public static final Character f5236c = Character.valueOf('ت');
    public static final Character f5237d = Character.valueOf('ث');
    public static final Character f5238e = Character.valueOf('ج');
    public static final Character f5239f = Character.valueOf('ح');
    public static final Character f5240g = Character.valueOf('خ');
    public static final Character f5241h = Character.valueOf('د');
    public static final Character f5242i = Character.valueOf('ذ');
    public static final Character f5243j = Character.valueOf('ر');
    public static final Character f5244k = Character.valueOf('ز');
    public static final Character f5245l = Character.valueOf('س');
    public static final Character f5246m = Character.valueOf('ش');
    public static final Character f5247n = Character.valueOf('ص');
    public static final Character f5248o = Character.valueOf('ض');
    public static final Character f5249p = Character.valueOf('ط');
    public static final Character f5250q = Character.valueOf('ظ');
    public static final Character f5251r = Character.valueOf('ع');
    public static final Character f5252s = Character.valueOf('غ');
    public static final Character f5253t = Character.valueOf('ف');
    public static final Character f5254u = Character.valueOf('ق');
    public static final Character f5255v = Character.valueOf('ك');
    public static final Character f5256w = Character.valueOf('ل');
    public static final Character f5257x = Character.valueOf('م');
    public static final Character f5258y = Character.valueOf('ن');
    public static final Character f5259z = Character.valueOf('ه');

    public static int m8605a(int[] iArr) {
        for (int i = 1; i < iArr.length; i++) {
            if (!C0873a.m8611d(iArr[i])) {
                return i;
            }
        }
        return 0;
    }

    public static int m8606a(int[] iArr, int i) {
        int i2 = 0;
        while (i < iArr.length) {
            if (C0873a.m8607a(iArr[i]) || C0873a.m8609b(iArr[i]) || iArr[i] == 0) {
                return i;
            }
            i2 = i;
            i++;
        }
        return i2;
    }

    public static boolean m8607a(int i) {
        return (i >= f5236c.charValue() && i <= f5252s.charValue()) || ((i >= f5253t.charValue() && i <= f5209B.charValue()) || i == f5218K.charValue() || i == f5210C.charValue() || i == f5217J.charValue() || i == f5234a.charValue() || i == f5235b.charValue() || i == f5211D.charValue() || i == f5212E.charValue() || i == f5213F.charValue() || i == f5214G.charValue() || i == f5216I.charValue() || i == f5215H.charValue() || i == f5219L.charValue());
    }

    public static int[] m8608a(String str, int i) {
        int[] iArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = i + i2;
            if (i3 < str.length()) {
                iArr[i2] = str.codePointAt(i3);
            }
        }
        return iArr;
    }

    public static boolean m8609b(int i) {
        return i == f5220M.charValue() || i == f5221N.charValue() || i == f5222O.charValue() || i == f5223P.charValue() || ((i >= f5224Q.charValue() && i <= f5225R.charValue()) || ((i >= f5226S.charValue() && i <= f5227T.charValue()) || ((i >= f5228U.charValue() && i <= f5229V.charValue()) || ((i >= f5230W.charValue() && i <= f5231X.charValue()) || ((i >= f5232Y.charValue() && i <= f5233Z.charValue()) || ((i >= aa.charValue() && i <= ab.charValue()) || i == ac.charValue()))))));
    }

    public static boolean m8610c(int i) {
        return C0873a.m8607a(i) || C0873a.m8609b(i);
    }

    public static boolean m8611d(int i) {
        return i == at.charValue() || i == au.charValue() || i == av.charValue() || i == aw.charValue() || i == ax.charValue() || i == ay.charValue() || i == az.charValue() || i == aA.charValue() || i == aB.charValue() || i == aC.charValue();
    }

    public static boolean m8612e(int i) {
        return i == aE.charValue() || i == aF.charValue() || i == aG.charValue() || i == aH.charValue() || i == aI.charValue() || i == aJ.charValue();
    }

    public static boolean m8613f(int i) {
        return (C0873a.m8612e(i) || C0873a.m8611d(i) || i == 32 || i == aD.charValue()) ? false : true;
    }
}
