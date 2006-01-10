
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2006, Dawid Weiss, Stanisław Osiński.
 * Portions (C) Contributors listed in "carrot2.CONTRIBUTORS" file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package com.dawidweiss.carrot.tokenizer;


/**
 * Implementation of abstract Tokenizer class generated by JavaCC parser generator.
 */
class TokenizerImpl
    implements TokenizerImplConstants
{
    public TokenizerImplTokenManager token_source;
    SimpleCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private int jj_gen;
    private final int [] jj_la1 = new int[0];
    private final int [] jj_la1_0 = {  };

    public TokenizerImpl(java.io.InputStream stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new TokenizerImplTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }

    public void ReInit(java.io.InputStream stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }

    public TokenizerImpl(java.io.Reader stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new TokenizerImplTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }

    public void ReInit(java.io.Reader stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }

    public TokenizerImpl(TokenizerImplTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }

    public void ReInit(TokenizerImplTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 0; i++)
        {
            jj_la1[i] = -1;
        }
    }


    private final Token jj_consume_token(int kind)
        throws ParseException
    {
        Token oldToken;

        if ((oldToken = token).next != null)
        {
            token = token.next;
        }
        else
        {
            token = token.next = token_source.getNextToken();
        }

        jj_ntk = -1;

        if (token.kind == kind)
        {
            jj_gen++;

            return token;
        }

        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }


    public final Token getNextToken()
    {
        if (token.next != null)
        {
            token = token.next;
        }
        else
        {
            token = token.next = token_source.getNextToken();
        }

        jj_ntk = -1;
        jj_gen++;

        return token;
    }


    public final Token getToken(int index)
    {
        Token t = token;

        for (int i = 0; i < index; i++)
        {
            if (t.next != null)
            {
                t = t.next;
            }
            else
            {
                t = t.next = token_source.getNextToken();
            }
        }

        return t;
    }


    private final int jj_ntk()
    {
        if ((jj_nt = token.next) == null)
        {
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        }
        else
        {
            return (jj_ntk = jj_nt.kind);
        }
    }

    private java.util.Vector jj_expentries = new java.util.Vector();
    private int [] jj_expentry;
    private int jj_kind = -1;

    public final ParseException generateParseException()
    {
        jj_expentries.removeAllElements();

        boolean [] la1tokens = new boolean[19];

        for (int i = 0; i < 19; i++)
        {
            la1tokens[i] = false;
        }

        if (jj_kind >= 0)
        {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }

        for (int i = 0; i < 0; i++)
        {
            if (jj_la1[i] == jj_gen)
            {
                for (int j = 0; j < 32; j++)
                {
                    if ((jj_la1_0[i] & (1 << j)) != 0)
                    {
                        la1tokens[j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < 19; i++)
        {
            if (la1tokens[i])
            {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.addElement(jj_expentry);
            }
        }

        int [][] exptokseq = new int[jj_expentries.size()][];

        for (int i = 0; i < jj_expentries.size(); i++)
        {
            exptokseq[i] = (int []) jj_expentries.elementAt(i);
        }

        return new ParseException(token, exptokseq, tokenImage);
    }


    public final void enable_tracing()
    {
    }


    public final void disable_tracing()
    {
    }
}
