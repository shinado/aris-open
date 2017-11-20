package indi.ss.pipes.androidmanifest;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class AndroidManifestPipe extends SimpleActionPipe {

    public AndroidManifestPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        callback.onOutput(getDisplayName());
    }

    @Override
    public String getDisplayName() {
        return "androidmanifest";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (previous.get().getId() == PConstants.ID_APPLICATION) {
            try {
                String[] split = input.split(",");
                String packageName = split[0];

                AssetManager mCurAm = context.createPackageContext(packageName, 0).getAssets();
                Resources initRes = new Resources(mCurAm, context.getResources()
                        .getDisplayMetrics(), null);

                XmlResourceParser xml = mCurAm.openXmlResourceParser("AndroidManifest.xml");

                callback.onOutput(getXMLText(xml, initRes));

//                    PackageManager pm = getContext().getPackageManager();
//                    ActivityInfo ai = pm.getActivityInfo(new ComponentName(split[0], split[1]),
//                            PackageManager.GET_META_DATA);
//
//                    ZipFile zipFile = new ZipFile(ai.applicationInfo.publicSourceDir);
//                    ZipEntry manifest = zipFile.getEntry("AndroidManifest.xml");
//                    if (manifest != null) {
//                        InputStream in = zipFile.getInputStream(manifest);
//                        int length = in.available();
//                        byte[] buffer = new byte[length];
//                        in.read(buffer);
//                        in.close();
//                        String value = new String(buffer, "UTF-8");
//                        callback.onOutput(value);
//                    }
//                    zipFile.close();
                } catch(Exception e){
                    e.printStackTrace();
                    callback.onOutput(e.getMessage());
                }
            } else{
                callback.onOutput("Target " + previous.get().getDisplayName() + " is not an application.");
            }
        }

    private void insertSpaces(StringBuffer sb, int num) {
        if (sb == null)
            return;
        for (int i = 0; i < num; i++)
            sb.append(" ");
    }

    private String getXMLText(XmlResourceParser xrp,
                              Resources currentResources) throws XmlPullParserException, IOException {
        StringBuffer sb = new StringBuffer();
        int indent = 0;
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            // for sb
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    indent += 1;
                    sb.append("\n");
                    insertSpaces(sb, indent);
                    sb.append("<" + xrp.getName());
                    sb.append(getAttribs(xrp, currentResources));
                    sb.append(">");
                    break;
                case XmlPullParser.END_TAG:
                    indent -= 1;
                    sb.append("\n");
                    insertSpaces(sb, indent);
                    sb.append("</" + xrp.getName() + ">");
                    break;

                case XmlPullParser.TEXT:
                    sb.append("" + xrp.getText());
                    break;

                case XmlPullParser.CDSECT:
                    sb.append("<!CDATA[" + xrp.getText() + "]]>");
                    break;

                case XmlPullParser.PROCESSING_INSTRUCTION:
                    sb.append("<?" + xrp.getText() + "?>");
                    break;

                case XmlPullParser.COMMENT:
                    sb.append("<!--" + xrp.getText() + "-->");
                    break;
//                default:
//                    sb.append(xrp.getText());
            }
            eventType = xrp.nextToken();
        }
        return sb.toString();
    }

    /**
     * returns the value, resolving it through the provided resources if it
     * appears to be a resource ID. Otherwise just returns what was provided.
     *
     * @param in String to resolve
     * @param r  Context appropriate resource (system for system, package's for
     *           package)
     * @return Resolved value, either the input, or some other string.
     */
    private String resolveValue(String in, Resources r) {
        if (in == null || !in.startsWith("@") || r == null)
            return in;
        try {
            int num = Integer.parseInt(in.substring(1));
            return r.getString(num);
        } catch (NumberFormatException e) {
            return in;
        } catch (RuntimeException e) {
            // formerly noted errors here, but simply not resolving works better
            return in;
        }
    }

    private CharSequence getAttribs(XmlResourceParser xrp,
                                    Resources currentResources) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < xrp.getAttributeCount(); i++)
            sb.append("\n" + xrp.getAttributeName(i) + "=\""
                    + resolveValue(xrp.getAttributeValue(i), currentResources)
                    + "\"");
        return sb;
    }


}
