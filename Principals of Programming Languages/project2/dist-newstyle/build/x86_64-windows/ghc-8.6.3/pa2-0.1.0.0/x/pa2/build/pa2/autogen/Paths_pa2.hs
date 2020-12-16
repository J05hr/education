{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
module Paths_pa2 (
    version,
    getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

#if defined(VERSION_base)

#if MIN_VERSION_base(4,0,0)
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#else
catchIO :: IO a -> (Exception.Exception -> IO a) -> IO a
#endif

#else
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#endif
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []
bindir, libdir, dynlibdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\bin"
libdir     = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\x86_64-windows-ghc-8.6.3\\pa2-0.1.0.0-inplace-pa2"
dynlibdir  = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\x86_64-windows-ghc-8.6.3"
datadir    = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\x86_64-windows-ghc-8.6.3\\pa2-0.1.0.0"
libexecdir = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\pa2-0.1.0.0-inplace-pa2\\x86_64-windows-ghc-8.6.3\\pa2-0.1.0.0"
sysconfdir = "C:\\Users\\J05h\\AppData\\Roaming\\cabal\\etc"

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "pa2_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "pa2_libdir") (\_ -> return libdir)
getDynLibDir = catchIO (getEnv "pa2_dynlibdir") (\_ -> return dynlibdir)
getDataDir = catchIO (getEnv "pa2_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "pa2_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "pa2_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "\\" ++ name)
