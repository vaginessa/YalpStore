/*
 * Yalp Store
 * Copyright (C) 2018 Sergey Yeriomin <yeriomin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.yeriomin.yalpstore.selfupdate;

import android.content.Context;
import android.content.pm.PackageManager;

import com.github.yeriomin.yalpstore.BuildConfig;

import java.util.HashSet;
import java.util.Set;

public class Signature {

    static private final String SIGNATURE_FDROID = "3082035f30820247a003020102020468ba885b300d06092a864886f70d01010b05003060310b300906035504061302554b310c300a060355040813034f5247310c300a060355040713034f524731133011060355040a130a6664726f69642e6f7267310f300d060355040b13064644726f6964310f300d060355040313064644726f6964301e170d3136313233303232303834325a170d3434303531373232303834325a3060310b300906035504061302554b310c300a060355040813034f5247310c300a060355040713034f524731133011060355040a130a6664726f69642e6f7267310f300d060355040b13064644726f6964310f300d060355040313064644726f696430820122300d06092a864886f70d01010105000382010f003082010a0282010100b10ec12303ee581f42f98fab7a31849c7fac9bd3e222b738c12b0fbfcb3d2b50589381da7c4ff42d8c412806188ea6806e0f54595afc651696d21053f89a4dae42ac02a469c8828a5ffc49954c60f9ccc66d60f0863928e0d2b17f8e9103a11d6056c53935abd64c984c3e48dc4611efa2bea89ac48bdb8e1257f23b193567262dea3b39bdc1fb4cf6852155f44920aee08d0dbc458cd43c24f4262ac6f293d88b51b7c7443c321ad77619e270f427fee8109772566aa998ba927c9ed2dc4c48b517c1b37fe1c65a8c1681a542fda60182cf3fb600f8584668815a4bceda81e708a2c815dd85abbabe88cc5719f8a5326284bafb5c4121596bd67f45ed7ec5630203010001a321301f301d0603551d0e04160414e3e2d2155bd13aba9aad5a851634f16e020abb64300d06092a864886f70d01010b05000382010100a431e1424afa29a50bb2ecdf710aa757f09835b7dcd484f2d7738ef58b1dc5928080c885a0082520b248e940b5c561b5bf7f49ea1436bd2d659f4eb432d2743a43bb0756ee236fa17bd4e77a00cd995d0f769d1ab9012382d960f04c8b920cb4c90bc14aa9a93de97387ef00abd86101b4b0be5b50670ff6d271d7719f044e541acaad219a8e02bf714ec5f27a1c2d81bc33a3feaa55cfa53a6488b3b057e97e66545741e41a5194f25f5837c639b190287c47feb6f6c88adcc222f7cdd3bd55d45f79d3f212c547ba9e2f24d286824af6eec7359adba5bf03e1e5b40e47dad059b799105c58a57a3142d7c7f270c9728a84f1992fb4a30da4cbe74803079221";
    static private final String SIGNATURE_GITHUB = "308203453082022da0030201020204044417d8300d06092a864886f70d01010b05003053310b3009060355040613025255310f300d0603550408130652757373696131193017060355040713105361696e742d50657465727362757267311830160603550403130f53657267657920596572696f6d696e301e170d3137303931363230343833305a170d3432303931303230343833305a3053310b3009060355040613025255310f300d0603550408130652757373696131193017060355040713105361696e742d50657465727362757267311830160603550403130f53657267657920596572696f6d696e30820122300d06092a864886f70d01010105000382010f003082010a028201010087ff8de3ce6e89e55dd5a6bb2af95952f32e4bdce4c370e7744bba3d16aba4a737a0c77f95a0c1ee43b7ec4b37de29c5e163e751cdb6d17f1be9eee29e724a1e65df4de68c1dee9bf8220b3bdc87554994f2952bfea6cad53b7065168c9fe97c64ed5ad51b7831256cb9767b433827525c689df3826d3b65a6122c37f866d6c65d52471925cd962f3ef598186287059e70f89a6155e32e1a402f68e9a97170927999dbebed7b0738215614680108ddfc9834ad9a47fcade6f6359e044be00650f2435f90f05f1be68d7f61eddd469d57b40117a7fbfb774100f8fc1ca9d11266a0edfcc496191e230439933d2a14e57157e685ee0cc93075e9956280f91128cf0203010001a321301f301d0603551d0e0416041447ba1bad4480fa4af1730a992a2b7976aa019079300d06092a864886f70d01010b050003820101005291a0f5a7e090a42b1fc895f27db3785e9abc4e973718aa999c17ec372ab3f7b3e5aa74f1f15a82fff1313c7d2034f18a2918e661fba20fc61f3ce489675ddf80e8510fea7f323c3037ec9356aa15b231f9a8f911148e4c9471b9a5649942f8adc29bf0ddda418ddbd10aee9a384803e9c9cb23f17e05cd836cd65c7081cfa79109b8a7b106e1bbcd3ccf8c28b109842a745b2ea3d1aa177bd295087e40cdc7b765e981ea74b975650eff7886f8fec2d3a1e03b18be027abdd691257d4213a7f12a33dcd890db3aae4a5c9a1242ce2bc8be3706535ccf8d2fd93f5d38191c4acb118d3b9c2e342f8fc825ef316aff8484a5d6230b80f11314816ff4d9c03c5e";

    static private Set<String> currentSignatures;

    static public boolean isFdroid(Context context) {
        return getSignatureSet(context).size() == 1 && getSignatureSet(context).contains(SIGNATURE_FDROID);
    }

    static public boolean isGithub(Context context) {
        return getSignatureSet(context).size() == 1 && getSignatureSet(context).contains(SIGNATURE_GITHUB);
    }

    static private Set<String> getSignatureSet(Context context) {
        if (null != currentSignatures) {
            return currentSignatures;
        }
        currentSignatures = new HashSet<>();
        try {
            for (android.content.pm.Signature signature: context.getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES).signatures) {
                currentSignatures.add(signature.toCharsString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Unlikely
        }
        return currentSignatures;
    }
}
