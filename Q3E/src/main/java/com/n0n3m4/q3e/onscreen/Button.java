package com.n0n3m4.q3e.onscreen;

import android.view.View;

import com.n0n3m4.q3e.Q3EControlView;
import com.n0n3m4.q3e.Q3EKeyCodes;
import com.n0n3m4.q3e.Q3EUtils;
import com.n0n3m4.q3e.gl.GL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class Button extends Paintable implements TouchListener
{
    static ArrayList<Integer> heldarr = new ArrayList<>(0);

    public View view;
    public int cx;
    public int cy;
    public int width;
    public int height;
    float[] verts = {-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
    FloatBuffer verts_p;
    float[] texcoords = {0, 0, 0, 1, 1, 1, 1, 0};
    FloatBuffer tex_p;
    byte[] indices = {0, 1, 2, 0, 2, 3};
    ByteBuffer inds_p;
    public int tex_ind;
    public int keycode;
    public int style;
    public float initalpha;
    public boolean canbeheld = false;
    private final int m_width_2;

    public Button(View vw, GL10 gl, int center_x, int center_y, int w, int h, String texid, int keyc, int stl, boolean canbheld, float a)
    {
        view = vw;
        cx = center_x;
        cy = center_y;
        alpha = a;
        initalpha = alpha;
        width = w;
        height = h;
        keycode = keyc;
        style = stl;
        canbeheld = canbheld;
        for (int i = 0; i < verts.length; i += 2)
        {
            verts[i] = verts[i] * w + cx;
            verts[i + 1] = verts[i + 1] * h + cy;
        }

        verts_p = ByteBuffer.allocateDirect(4 * verts.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verts_p.put(verts);
        verts_p.position(0);

        inds_p = ByteBuffer.allocateDirect(indices.length);
        inds_p.put(indices);
        inds_p.position(0);

        tex_p = ByteBuffer.allocateDirect(4 * texcoords.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        tex_p.put(texcoords);
        tex_p.position(0);

        tex_androidid = texid;

        m_width_2 = width * width;
    }

    @Override
    public void loadtex(GL10 gl)
    {
        tex_ind = GL.loadGLTexture(gl, Q3EUtils.ResourceToBitmap(view.getContext(), tex_androidid));
    }

    @Override
    public void Paint(GL11 gl)
    {
        super.Paint(gl);
        GL.DrawVerts(gl, tex_ind, 6, tex_p, verts_p, inds_p, 0, 0, red, green, blue, alpha);
    }

    private int lx;
    private int ly;

    @Override
    public boolean onTouchEvent(int x, int y, int act)
    {
        Q3EControlView controlView = (Q3EControlView) (this.view);
        if (canbeheld)
        {
            if (act == 1)
            {
                if (!heldarr.contains(keycode))
                {
                    controlView.sendKeyEvent(true, keycode, 0);
                    heldarr.add(keycode);
                    alpha = Math.min(initalpha * 2, 1f);
                }
                else
                {
                    controlView.sendKeyEvent(false, keycode, 0);
                    heldarr.remove(Integer.valueOf(keycode));
                    alpha = initalpha;
                }
            }
            return true;
        }

        if (keycode == Q3EKeyCodes.K_VKBD)
        {
            if (act == 1)
                Q3EUtils.togglevkbd(this.view);
            return true;
        }

        if (act == 1)
        {
            lx = x;
            ly = y;
            controlView.sendKeyEvent(true, keycode, 0);
        }
        else if (act == -1)
            controlView.sendKeyEvent(false, keycode, 0);

        if (keycode == Q3EKeyCodes.KeyCodes.K_MOUSE1)
        {
            if (Q3EUtils.q3ei.callbackObj.notinmenu)
            {
                controlView.sendMotionEvent(x - lx, y - ly);
                lx = x;
                ly = y;
            }
            else
            {
                controlView.sendMotionEvent(0, 0);//???
            }
        }
        return true;
    }

    @Override
    public boolean isInside(int x, int y)
    {
        if (style == 0)
        {
            int dx = cx - x;
            int dy = cy - y;
            return 4 * (dx * dx + dy * dy) <= m_width_2;
        }
        else if (style == 1)
        {
            int dx = x - cx;
            int dy = cy - y;
            return (((dy) <= (dx)) && (2 * Math.abs(dx) < width) && (2 * Math.abs(dy) < height));
        }
        else if (style == 2)
        {
            int dx = x - cx;
            int dy = cy - y;
            return ((2 * Math.abs(dx) < width) && (2 * Math.abs(dy) < height));
        }
        else if (style == 3)
        {
            int dx = cx - x;
            int dy = y - cy;
            return (((dy) <= (dx)) && (2 * Math.abs(dx) < width) && (2 * Math.abs(dy) < height));
        }
        return false;
    }

    public static Button Move(Button tmp, GL10 gl)
    {
        Button newb = new Button(tmp.view, gl, tmp.cx, tmp.cy, tmp.width, tmp.height, tmp.tex_androidid, tmp.keycode, tmp.style, tmp.canbeheld, tmp.alpha);
        newb.tex_ind = tmp.tex_ind;
        return newb;
    }

    public void Translate(int dx, int dy)
    {
        cx += dx;
        cy += dy;
    }

    public void SetPosition(int x, int y)
    {
        cx = x;
        cy = y;
    }
}