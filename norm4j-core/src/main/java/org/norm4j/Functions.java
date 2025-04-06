/*
 * Copyright 2025 April Software
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.norm4j;

import java.util.List;

public class Functions
{
    public Functions()
    {
    }

    public static <T, R> Expression coalesce(final Object... values)
    {
        return new Expression()
        {
            public String build(TableManager tableManager, 
                    List<Object> parameters)
            {
                StringBuilder expression;
                boolean onlyNull = true;

                expression = new StringBuilder();

                for (Object value : values)
                {
                    if (value != null)
                    {
                        onlyNull = false;

                        break;
                    }
                }

                if (onlyNull)
                {
                    expression.append("NULL");
                }
                else
                {
                    expression.append("COALESCE(");

                    for (int i = 0; i < values.length; i++)
                    {
                        Object value;
    
                        if (i > 0)
                        {
                            expression.append(", ");
                        }
    
                        value = values[i];
    
                        if (value == null)
                        {
                            expression.append("NULL");
                        }
                        else
                        {
                            expression.append("?");
    
                            parameters.add(value);
                        }
                    }
    
                    expression.append(", NULL)");
                }

                return expression.toString();
            }
        };
    }
}
