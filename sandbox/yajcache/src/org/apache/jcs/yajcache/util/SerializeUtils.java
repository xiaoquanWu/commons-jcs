
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jcs.yajcache.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.lang.SerializationUtils;
import org.apache.jcs.yajcache.annotate.*;

/**
 * @author Hanson Char
 */
@CopyRightApache
public enum SerializeUtils {
    inst;
    /**
     * Duplicates the given object.
     *
     * @return a duplicate of the given Serializable object,
     * short-cutting the deep clone process if possible.
     */
    public <V extends Serializable> V dup(V obj) {
        Class k = null;
        
        if (obj == null 
        ||  ClassUtils.inst.isImmutable(k=obj.getClass()))
            return obj;
        Class t = k.getComponentType();
        
        if (t != null) {
            // an array.
            if (ClassUtils.inst.isImmutable(t))
            {
                // array elements are immutable.
                // short cut via shallow clone.
                return this.cloneArray(obj);
            }
        }
        // deep clone.
        return (V)SerializationUtils.clone(obj);
    }
    private @NonNullable <A> A cloneArray(@NonNullable A a) {
        int len = Array.getLength(a);
	Object result = Array.newInstance(a.getClass().getComponentType(), len);
        System.arraycopy(a, 0, result, 0, len);
        return (A)result;
    }
//    public Class<?> getLeaveComponentType(Class<?> k) {
//        if (k == null)
//            return k;
//        if (k.isArray()) {
//            return this.getLeaveComponentType(k.getComponentType());
//        }
//        return k.getClass();
//    }
}